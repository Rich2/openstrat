/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

/** Function object to stage the module main jars built under Mill. */
trait MillStageJars
{
  val versionStr: String = "0.3.5"

  val modPairs: ArrPairStr[String] = StrStrPairArr("Util", "rutil", "Geom", "geom", "Tiling", "tiling", "EGrid", "egrid", "Apps", "apps")

  def apply(stagingPath: String): Unit =
  {
    val sharedPath: String = stagingPath / "libShared"
    mkDirExist(sharedPath).forSucc { res1 =>
      projPathDo { projPath =>
        val f1: ErrBiAcc[Exception, FileCopied] = action(projPath, sharedPath)
        debvar(f1)
      }
    }
  }

  def action(projPath: DirsAbs, sharedPath: String): ErrBiAcc[Exception, FileCopied]

  /** Copies a main jar to the libShared staging folder. */
  def mainCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBi[Exception, FileCopied] =
    fileCopy(projPath.asStr / "out" / srcStr / "jar.dest/out.jar", sharedPath / destStr + "-" + versionStr + ".jar")

  /** Copies a main jar to the libShared staging folder. */
  def javadocCopy(projPath: DirsAbs, sharedPath: String, srcStr: String, destStr: String): ErrBi[Exception, FileCopied] =
    fileCopy(projPath.asStr / "out" / srcStr / "docJar.dest/out.jar", sharedPath / destStr + "-" + versionStr + ".javadoc.jar")
}

object MillJars extends MillStageJars
{
  def main(args: Array[String]): Unit = stagingPathDo { stagingPath => apply(stagingPath.asStr) }

  override def action(projPath: DirsAbs, sharedPath: String): ErrBiAcc[Exception, FileCopied] =
    modPairs.flatMapErrBiAcc(p => ErrBiAcc(mainCopy(projPath, sharedPath, p.a1, p.a2), javadocCopy(projPath, sharedPath, p.a1, p.a2)))
}

/** Function object to stage the module main jars built under Mill. */
object MillStageMainJars extends MillStageJars
{
  override def action(projPath: DirsAbs, sharedPath: String): ErrBiAcc[Exception, FileCopied] =
  {
    modPairs.mapErrBiAcc (p => mainCopy(projPath, sharedPath, p.a1, p.a2))
  }
}