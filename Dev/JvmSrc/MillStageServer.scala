/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc._

/** application used by mill to stage openstrat files for a passive server. */
object MillStageServer extends StagingBuild
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
    deb(eGameJsFiles.msg2ErrsSummary("JavaScript", "to earthgames directory"))

    val otherPath: String = stagePath /% "otherapps"
    val otherBi: ExcIOMon[DirExists] = mkDirExist(otherPath)
    val otherJsFiles = otherBi.flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js"
        val destStr: String = otherPath / ga.filesStem + ".js"
        fileCopy(fromStr, destStr)
      }
    }
    println(otherJsFiles.msg2ErrsSummary("JavaScript", "to otherapps directory"))

    val egridPath: String = stagePath /% "egrids"
    val eGridBi: ExcIOMon[DirExists] = mkDirExist(egridPath)
    val egridJsFiles = eGridBi.flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest/main.js"
        val destStr: String = egridPath / ga.filesStem + ".js"
        fileCopy(fromStr, destStr)
      }
    }
    println(egridJsFiles.msg2ErrsSummary("JavaScript", "to egrid directory"))
    egridJsFiles.errsPrint
  }
}