/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pDoc.*, pWeb.SwVersion, utiljvm.*

class Module(val modName: String, fileStem: String, deps: RArr[Module])

object Module
{
  def apply(modName: String, fileStem: String, deps: Module*): Module = new Module(modName, fileStem, deps.toRArr)
}

/** Stages jars built under Mill. */
trait MillStageJars
{ /** The openstrat version of the jars you wish to stage. */
  val version: SwVersion = SwVersion(0, 3, 11)
  val scalaVersion = SwVersion(3, 8, 2)

  val pomMods1: RArr[OsModulePomVerless] = RArr(UtilPommer, GeomPommer, TilingPommer, EGridPommer, UtilJsPommer)

  val repPairs: ArrPairStr[String] = StrStrPairArr("Util", "rutil", "Geom", "geom", "Tiling", "tiling", "EGrid", "egrid")
  /** Pairs of the module names and the name stem for their assets. */
  val modPairs: ArrPairStr[String] = repPairs +% PairStrElem("Apps", "apps")

  def apply(stagingPath: DirsAbs): Unit =
  { val res: ErrBiAcc[Throwable, FileWritten] = projPathFind.flatMapAcc { projPath =>  action(projPath, stagingPath) }
    deb(res.errsSummary)
  }

  def action(projPath: DirsAbs, stageDirStr: DirsAbs): ErrBiAcc[Exception, FileWritten]

  /** Copies a Mill built main jar from an "out" directory subdirectory to the given staging folder. */
  def millMainCopy(projPath: DirsAbs, stageDir: DirsAbs, moduleDir: DirsRel, fileStemStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDir, moduleDir, fileStemStr, "jar.dest", "")

  /** Copies a Mill built Javadoc jar from an "out" directory subdirectory to the given staging folder. */
  def millJavadocCopy(projPath: DirsAbs, stageDir: DirsAbs, moduleDir: DirsRel, fileStemStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDir, moduleDir, fileStemStr, "docJar.dest", "-javadoc")

  /** Copies a Mill built sources jar from an "out" directory subdirectory to the given staging folder. */
  def millSrcJarCopy(projPath: DirsAbs, stageDir: DirsAbs, moduleDir: DirsRel, fileStemStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDir, moduleDir, fileStemStr, "sourceJar.dest", "-sources")

  /** Copies a Mill built jar from an "out" directory subdirectory to the given staging directory. */
  def millJarCopy(projPath: DirsAbs, stageDir: DirsAbs, moduleDir: DirsRel, fileStemStr: String, millEndDirStr: String, jarTypeStr: String):
    ErrBi[Exception, FileWritten] =
    copyFile(projPath / "out" / moduleDir / millEndDirStr :/ "out.jar", stageDir :/ fileStemStr + "-" + version.str + jarTypeStr + ".jar")

  /** Copies prebuilt main, Javadoc and sources jars to the libShared staging folder. */
  def jars3Copy(projPath: DirsAbs, stageDirPath: DirsAbs, moduleDir: DirsRel, destStr: String): ErrBiAcc[Exception, FileWritten] =
    ErrBiAcc[Exception, FileWritten](
      millMainCopy(projPath,stageDirPath, moduleDir, destStr),
      millJavadocCopy(projPath, stageDirPath, moduleDir, destStr),
      millSrcJarCopy(projPath, stageDirPath, moduleDir, destStr))
}

/** Function object to stage the module all the JVM jars built under Mill. */
object MillJars extends MillStageJars
{
  def main(args: Array[String]): Unit = stagingPathDo { stagingPath => apply(stagingPath) }

  override def action(projPath: DirsAbs, stagingRootDir: DirsAbs): ErrBiAcc[Exception, FileWritten] =
  { val otherPairs: ArrPairStr[String] = StrStrPairArr("UtilJs", "rutiljs", "GeomFx", "geomfx", "GeomJs", "geomjs", "TilingJs", "tilingjs")
    val allPairs = modPairs ++ otherPairs
    val sharedPath: DirsAbs = stagingRootDir / "libShared"

    val pomWriter = OsPomsWriter()
    val res1: ErrBiAcc[Exception, FileWritten] =
      sharedPath.mkExist.flatMapAcc { res1 =>  allPairs.flatMapErrBiAcc { p => jars3Copy(projPath, sharedPath, DirsRel(p.a1), p.a2) } }

    val repositaryPath: DirsAbs = stagingRootDir / "richstrat"
    val res2: ErrBiAcc[Exception, FileWritten] = repositaryPath.mkExist.flatMapAcc{ r1 =>
      pomMods1.flatMapErrBiAcc { pm =>
        val modulePath: DirsAbs = repositaryPath / pm.artifactStr
        modulePath.mkExist
        val verPath: DirsAbs = modulePath / version.str
        verPath.mkExist
        debvar(verPath)
        jars3Copy(projPath, verPath, pm.moduleDir, pm.artifactStr) +% pomWriter.stagePom(verPath, pm.version(version, scalaVersion))
      }
    }
    res1 ++ res2
  }
}

/** Function object to stage the module main jars built under Mill. */
object MillStageMainJars extends MillStageJars
{
  override def action(projPath: DirsAbs, stageDir: DirsAbs): ErrBiAcc[Exception, FileWritten] =
  { modPairs.mapErrBiAcc (p => millMainCopy(projPath, stageDir, DirsRel(p.a1), p.a2))
  }
}