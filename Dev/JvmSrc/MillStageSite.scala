/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, webjvm.*, pDoc.*

/** application used by mill to stage openstrat files for a passive server. */
object MillStageSite extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { val stagePathBi = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    ossDirBi.forSucc{ ossDir =>
      stageBase(ossDir)
      useStaging(ossDir)
    }
  }

  def useStaging(stagePath: DirsAbs): Unit = projPathDo{ projPath =>
    val egPath: DirsAbs = stagePath / "earthgames"
    val eGameJsFiles: ErrBiAcc[Exception, JsFileWritten] = egPath.mkExist.mapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc{ ga =>
        val source: DirsAbs = projPath.outDir / "AppJs" / ga.jsMainStem / "fullLinkJS.dest"
        jsWithMapFileRenameCopy(source, egPath, ga.fileStemStr)
      }
    }
    deb(eGameJsFiles.msgErrsSummary("to earthgames directory"))

    val docPath: DirsAbs = stagePath / "Documentation"
    val jarApp: ErrBiAcc[Exception, FileWritten] = stageDocumentationJs(projPath, docPath)
    deb(jarApp.errsSummary)
    val otherPath: DirsAbs = stagePath / "otherapps"
    val otherBi: ExcIOMon[DirExists] = otherPath.mkExist
    val otherJsFiles = otherBi.mapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromDir: DirsAbs = projPath / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest"
        jsWithMapFileRenameCopy(fromDir, otherPath, ga.fileStemStr)
      }
    }
    deb(otherJsFiles.msgErrsSummary("to otherapps directory"))

    val egridPath: DirsAbs = stagePath / "egrids"
    val eGridBi: ExcIOMon[DirExists] = egridPath.mkExist
    val egridJsFiles = eGridBi.mapAcc { res =>
      AppPage.eGrids.mapErrBiAcc { ga =>
        val fromDir: DirsAbs = projPath / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest"
        jsWithMapFileRenameCopy(fromDir, egridPath, ga.fileStemStr)
      }
    }
    deb(egridJsFiles.msgErrsSummary("to egrid directory"))
    egridJsFiles.errsPrint
  }
}