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

  /** Pairs of the module names and the name stem for their assets. */
  val modPairs: ArrPairStr[String] = StrStrPairArr("Util", "rutil", "Geom", "geom", "Tiling", "tiling", "EGrid", "egrid", "Apps", "apps")

  def apply(stagingPath: String): Unit =
  {
    val sharedPath: String = stagingPath / "libShared"
    mkDirExist(sharedPath).forSucc { res1 =>
      projPathDo { projPath =>
        val res: ErrBiAcc[Exception, FileWritten] = action(projPath, sharedPath)
        deb(res.errsSummary)
      }
    }
  }

  def action(projPath: DirsAbs, sharedPath: String): ErrBiAcc[Exception, FileWritten]

  /** Copies a main jar to the libShared staging folder. */
  def mainCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    jarCopy(projPath, sharedPath, srcStr, destStr, "jar", "")

  /** Copies a Javadoc jar to the libShared staging folder. */
  def javadocCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    jarCopy(projPath, sharedPath, srcStr, destStr, "docJar", "-javadoc")

  /** Copies a sources jar to the libShared staging folder. */
  def sourceCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBi[Exception, FileWritten] =
    jarCopy(projPath, sharedPath, srcStr, destStr, "docJar", "-sources")

  /** Copies a main jar to the libShared staging folder. */
  def jarCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String, origFolder: String, assetStr: String): ErrBi[Exception, FileWritten] =
    copyFile(projPath.asStr / "out" / srcStr / origFolder + ".dest/out.jar", sharedPath / destStr + "-" + versionStr + assetStr + ".jar")

  /** Copies prebuilt main, javadoc and sources jars to the libShared staging folder. */
  def mainDocSourceCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBiAcc[Exception, FileWritten] =
    ErrBiAcc[Exception, FileWritten](mainCopy(projPath, sharedPath, srcStr, destStr), javadocCopy(projPath, sharedPath, srcStr, destStr),
    sourceCopy(projPath, sharedPath, srcStr, destStr))
}

/** Function object to stage the module all the JVM jars built under Mill. */
object MillJars extends MillStageJars
{
  def main(args: Array[String]): Unit = stagingPathDo { stagingPath => apply(stagingPath.asStr) }

  override def action(projPath: DirsAbs, sharedPath: String): ErrBiAcc[Exception, FileWritten] =
  { val otherPairs: ArrPairStr[String] = StrStrPairArr("UtilJs", "rutiljs", "GeomFx", "geomfx", "GeomJs", "geomjs", "TilingJs", "tilingjs")
    val allPairs = modPairs ++ otherPairs
    allPairs.flatMapErrBiAcc { p => mainDocSourceCopy(projPath, sharedPath, p.a1, p.a2) }
  }
}

/** Function object to stage the module main jars built under Mill. */
object MillStageMainJars extends MillStageJars
{
  override def action(projPath: DirsAbs, sharedPath: String): ErrBiAcc[Exception, FileWritten] =
  { modPairs.mapErrBiAcc (p => mainCopy(projPath, sharedPath, p.a1, p.a2))
  }
}