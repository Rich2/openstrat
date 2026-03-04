/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

class Module(val modName: String, fileStem: String, deps: RArr[Module])

object Module
{
  def apply(modName: String, fileStem: String, deps: Module*): Module = new Module(modName, fileStem, deps.toRArr)
}

/** Stages jars built under Mill. */
trait MillStageJars
{ /** The openstrat version of the jars you wish to stage. */
  val versionStr: String = "0.3.11"

  val repPairs = StrStrPairArr("Util", "rutil", "Geom", "geom", "Tiling", "tiling", "EGrid", "egrid")
  /** Pairs of the module names and the name stem for their assets. */
  val modPairs: ArrPairStr[String] = repPairs +% PairStrElem("Apps", "apps")

  def apply(stagingPath: DirsAbs): Unit =
  { val res: ErrBiAcc[Throwable, FileWritten] = projPathFind.flatMapAcc { projPath =>  action(projPath, stagingPath) }
    deb(res.errsSummary)
  }

  def action(projPath: DirsAbs, stageDirStr: DirsAbs): ErrBiAcc[Exception, FileWritten]

  /** Copies a Mill built main jar from an "out" directory subdirectory to the given staging folder. */
  def millMainCopy(projPath: DirsAbs, stageDir: DirsAbs, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDir, srcStr, destStr, "jar", "")

  /** Copies a Mill built Javadoc jar from an "out" directory subdirectory to the given staging folder. */
  def millJavadocCopy(projPath: DirsAbs, stageDir: DirsAbs, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDir, srcStr, destStr, "docJar", "-javadoc")

  /** Copies a Mill built sources jar from an "out" directory subdirectory to the given staging folder. */
  def millSrcJarCopy(projPath: DirsAbs, stageDir: DirsAbs, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDir, srcStr, destStr, "docJar", "-sources")

  /** Copies a Mill built jar from an "out" directory subdirectory to the given staging directory. */
  def millJarCopy(projPath: DirsAbs, stageDir: DirsAbs, millmoduleStr: String, destStr: String, origFolder: String, assetStr: String): ErrBi[Exception, FileWritten] =
    copyFile(projPath.asStr / "out" / millmoduleStr / origFolder + ".dest/out.jar", stageDir.asStr / destStr + "-" + versionStr + assetStr + ".jar")

  /** Copies prebuilt main, Javadoc and sources jars to the libShared staging folder. */
  def jars3Copy(projPath: DirsAbs, stageDirPath: DirsAbs, srcStr: String, destStr: String): ErrBiAcc[Exception, FileWritten] =
    ErrBiAcc[Exception, FileWritten](millMainCopy(projPath, stageDirPath, srcStr, destStr), millJavadocCopy(projPath, stageDirPath, srcStr, destStr),
    millSrcJarCopy(projPath, stageDirPath, srcStr, destStr))
}

/** Function object to stage the module all the JVM jars built under Mill. */
object MillJars extends MillStageJars
{
  def main(args: Array[String]): Unit = stagingPathDo { stagingPath => apply(stagingPath) }

  override def action(projPath: DirsAbs, stagingRootDir: DirsAbs): ErrBiAcc[Exception, FileWritten] =
  { val otherPairs: ArrPairStr[String] = StrStrPairArr("UtilJs", "rutiljs", "GeomFx", "geomfx", "GeomJs", "geomjs", "TilingJs", "tilingjs")
    val allPairs = modPairs ++ otherPairs
    val sharedPath: DirsAbs = stagingRootDir / "libShared"
    val res1 = sharedPath.mkExist.flatMapAcc { res1 =>  allPairs.flatMapErrBiAcc { p => jars3Copy(projPath, sharedPath, p.a1, p.a2) } }
    val repositaryPath: DirsAbs = stagingRootDir / "repository"
    val res2 = repositaryPath.mkExist
    repPairs.foreach{pair =>
      val modulePath: DirsAbs = repositaryPath / pair.a2
      modulePath.mkExist
      val verPath: DirsAbs = modulePath / versionStr
      verPath.mkExist
      jars3Copy(projPath, verPath, pair.a1, pair.a2)
    }
    res1
  }
}

/** Function object to stage the module main jars built under Mill. */
object MillStageMainJars extends MillStageJars
{
  override def action(projPath: DirsAbs, stageDir: DirsAbs): ErrBiAcc[Exception, FileWritten] =
  { modPairs.mapErrBiAcc (p => millMainCopy(projPath, stageDir, p.a1, p.a2))
  }
}