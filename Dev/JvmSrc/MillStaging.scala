/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object MillStaging extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        stageBase(stagingPath)
        useStaging(stagingPath)
      }
    }
  }

  def useStaging(stagePath: DirsAbs): Unit = projPathDo{ projPath =>
    val egPath: String = stagePath /% "earthgames"
    val eGameJsFiles = mkDirExist(egPath).flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc(ga => fileCopy(projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js", egPath / ga.filesStem + ".js"))
    }
    println(eGameJsFiles.summaryStr("earthgames directory JavaScript"))

    val otherPath: String = stagePath /% "otherapps"
    val otherJsFiles = mkDirExist(otherPath).flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js"
        val destStr: String = otherPath / ga.filesStem + ".js"
        fileCopy(fromStr, destStr)
      }
    }
    println(otherJsFiles.summaryStr("otherapps directory JavaScript"))

    val egridPath: String = stagePath /% "egrids"
    val egridJsFiles = mkDirExist(egridPath).flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest/main.js"
        val destStr: String = egridPath / ga.filesStem + ".js"
        fileCopy(fromStr, destStr)
      }
    }
    println(egridJsFiles.summaryStr("egrid directory JavaScript"))
    egridJsFiles.errsPrint
  }
}

/** Function object to stage the module jars built under Mill. */
object MillStageJars
{ val versionStr: String = "0.3.5snap"

  def apply(stagingPath: String): Unit =
  { val sharedPath: String = stagingPath / "libShared"
    mkDirExist(sharedPath).forSucc { res1 =>
      projPathDo { projPath =>
        def fc(srcStr: String, destStr: String): ErrBi[Exception, FileCopied] =
          fileCopy(projPath.asStr / "out" / srcStr / "jar.dest/out.jar", sharedPath / destStr + "-" + versionStr + ".jar")
        val f1 = ErrBiAcc(fc("Util", "rutil"), fc("Geom", "geom"), fc("Tiling", "tiling"), fc("EGrid", "egrid"))
        debvar(f1)
      }
    }
  }
}