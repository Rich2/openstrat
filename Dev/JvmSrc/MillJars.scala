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

  /** Copies a main jar to the libShared staging folder. */
  def mainCopy(projPath: DirsAbs, stageDirStr: String, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDirStr, srcStr, destStr, "jar", "")

  /** Copies a Javadoc jar to the libShared staging folder. */
  def javadocCopy(projPath: DirsAbs, stageDirStr: String, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDirStr, srcStr, destStr, "docJar", "-javadoc")

  /** Copies a sources jar to the libShared staging folder. */
  def sourceCopy(projPath: DirsAbs, stageDirStr: String, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    millJarCopy(projPath, stageDirStr, srcStr, destStr, "docJar", "-sources")

  /** Copies a Mill created jar in an "out" directory subdirectory to the given staging directory. */
  def millJarCopy(projPath: DirsAbs, stageDirStr: String, millmoduleStr: String, destStr: String, origFolder: String, assetStr: String): ErrBi[Exception, FileWritten] =
    copyFile(projPath.asStr / "out" / millmoduleStr / origFolder + ".dest/out.jar", stageDirStr / destStr + "-" + versionStr + assetStr + ".jar")

  /** Copies prebuilt main, Javadoc and sources jars to the libShared staging folder. */
  def jars3Copy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBiAcc[Exception, FileWritten] =
    ErrBiAcc[Exception, FileWritten](mainCopy(projPath, sharedPath, srcStr, destStr), javadocCopy(projPath, sharedPath, srcStr, destStr),
    sourceCopy(projPath, sharedPath, srcStr, destStr))
}

/** Function object to stage the module all the JVM jars built under Mill. */
object MillJars extends MillStageJars
{
  def main(args: Array[String]): Unit = stagingPathDo { stagingPath => apply(stagingPath) }

  override def action(projPath: DirsAbs, stageingRootDir: DirsAbs): ErrBiAcc[Exception, FileWritten] =
  { val otherPairs: ArrPairStr[String] = StrStrPairArr("UtilJs", "rutiljs", "GeomFx", "geomfx", "GeomJs", "geomjs", "TilingJs", "tilingjs")
    val allPairs = modPairs ++ otherPairs
    val sharedPath: String = stageingRootDir.asStr / "libShared"
    val res1 = mkDirExist(sharedPath).flatMapAcc { res1 =>  allPairs.flatMapErrBiAcc { p => jars3Copy(projPath, sharedPath, p.a1, p.a2) } }
    val repPath: String = stageingRootDir.asStr / "repository"
    val res2 = mkDirExist(repPath)
    repPairs.foreach{pair =>
      val modPath = repPath / pair.a2
      mkDirExist(modPath)
      val verPath = modPath / versionStr
      mkDirExist(verPath)
      jars3Copy(projPath, verPath, pair.a1, pair.a2)
    }
    res1
  }
}

/** Function object to stage the module main jars built under Mill. */
object MillStageMainJars extends MillStageJars
{
  override def action(projPath: DirsAbs, stageDirStr: DirsAbs): ErrBiAcc[Exception, FileWritten] =
  { modPairs.mapErrBiAcc (p => mainCopy(projPath, stageDirStr.asStr, p.a1, p.a2))
  }
}