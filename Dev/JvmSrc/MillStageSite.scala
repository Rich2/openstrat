/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, utiljvm.*, pDoc.*

/** application used by mill to stage openstrat files for a passive server. */
object MillStageSite extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { val stagePathBi = stagingPathFind.flatMap(_.mkExist)
    val ossDirBi = stagePathBi.flatMap(_.mkSubExist("OpenstratSite"))
    ossDirBi.foreach{ ossDir =>
      stageBase(ossDir)
      useStaging(ossDir)
    }
  }

  def useStaging(stagePath: DirsAbs): Unit = projPathDo{ projPath =>
    val egPath: DirsAbs = stagePath / "earthgames"
    val eGameJsFiles: IOExcEither[JsWrittenResults] = egPath.mkExist.map{ res =>
      val res1: RArr[JsWrittenResults] = AppPage.eGameApps.map{ ga =>
        val source: DirsAbs = projPath.outDir / "AppJs" / ga.jsMainStem / "fullLinkJS.dest"
        jsWithMapFileRenameCopy(source, egPath, ga.fileStemStr)
      }
      JsWrittenResults.combine("", res1)
    }
    deb(eGameJsFiles.reportStr)

    val docPath: DirsAbs = stagePath / "Documentation"
    val jarApp: Either[IOExc, JsWrittenResults] = stageDocumentationJs(projPath, docPath)
    deb(jarApp.reportStr)
    val otherPath: DirsAbs = stagePath / "otherapps"
    val otherBi: IOExcEither[DirExists] = otherPath.mkExist
    val otherJsFiles = otherBi.map{ res =>
      val res2  = AppPage.otherApps.map { ga =>
        val fromDir: DirsAbs = projPath / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest"
        jsWithMapFileRenameCopy(fromDir, otherPath, ga.fileStemStr)
      }
      JsWrittenResults.combine("", res2)
    }
    deb(otherJsFiles.reportStr)

    val egridPath: DirsAbs = stagePath / "egrids"
    val eGridBi: IOExcEither[DirExists] = egridPath.mkExist
    val egridJsFiles: Either[IOExc, JsWrittenResults] = eGridBi.map { res =>
      val res2 = AppPage.eGrids.map{ ga =>
        val fromDir: DirsAbs = projPath / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest"
        jsWithMapFileRenameCopy(fromDir, egridPath, ga.fileStemStr)
      }
      JsWrittenResults.combine("", res2)
    }
    deb(egridJsFiles.reportStr)
    //egridJsFiles.errsPrint
  }
}