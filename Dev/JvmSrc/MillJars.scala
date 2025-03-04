/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object MillJars
{
  def main(args: Array[String]): Unit =
  {
    stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        deb("Mill Jars")
        MillStageMainJars(stagingPath.asStr)
      }
    }
  }
}

/** Function object to stage the module main jars built under Mill. */
object MillStageMainJars
{ val versionStr: String = "0.3.5"

  val modPairs: ArrPairStr[String] = StrStrPairArr("Util","rutil",  "Geom","geom",  "Tiling","tiling",   "EGrid","egrid",  "Apps","apps")

  def apply(stagingPath: String): Unit =
  { val sharedPath: String = stagingPath / "libShared"
    mkDirExist(sharedPath).forSucc { res1 =>
      projPathDo { projPath =>
        def fc(srcStr: String, destStr: String): ErrBi[Exception, FileCopied] =
          fileCopy(projPath.asStr / "out" / srcStr / "jar.dest/out.jar", sharedPath / destStr + "-" + versionStr + ".jar")
        val f1: ErrBiAcc[Exception, FileCopied] = modPairs.mapErrBiAcc(p => fc(p.a1, p.a2))
        debvar(f1)
      }
    }
  }
}