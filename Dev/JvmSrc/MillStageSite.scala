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
    val eGameJsFiles: ErrBiAcc[Exception, JsFileWritten] = egPath.mkExist.flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc{ ga =>
        val source: DirsAbsStem = projPath.out / "AppJs" / ga.jsMainStem / "fullLinkJS.dest" :-/ "main"
        jsWithMapFileCopy(source, egPath :-/ ga.fileStemStr)
      }
    }
    deb(eGameJsFiles.msgErrsSummary("to earthgames directory"))

    val docPath: DirsAbs = stagePath / "Documentation"
    val jarApp: ErrBi[Exception, JarFileWritten] = docPath.mkExist.flatMap { res =>
      jsWithMapFileCopy(projPath / "out/DevDocJs" / "fullLinkJS.dest" :-/ "main", docPath  :-/ "tomcat")
      utiljvm.jarFileCopy(projPath.asStr / "out/DevFx/assembly.dest/out", (docPath / "osapp").asStr)//needs improving
    }
    deb(jarApp.reportStr)
    val otherPath: DirsAbs = stagePath / "otherapps"
    val otherBi: ExcIOMon[DirExists] = otherPath.mkExist
    val otherJsFiles = otherBi.flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromStem: DirsAbsStem = projPath / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest" :-/ "main"
        val destStem: DirsAbsStem = otherPath :-/ ga.fileStemStr
        jsMapFileCopy(fromStem, destStem)
        jsFileCopy(fromStem, destStem)
      }
    }
    deb(otherJsFiles.msgErrsSummary("to otherapps directory"))

    val egridPath: DirsAbs = stagePath / "egrids"
    val eGridBi: ExcIOMon[DirExists] = egridPath.mkExist
    val egridJsFiles = eGridBi.flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc { ga =>
        val fromStem: DirsAbsStem = projPath / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest" :-/ "main"
        val destStem: DirsAbsStem = egridPath :-/ ga.fileStemStr
        jsFileCopy(fromStem, destStem)
      }
    }
    deb(egridJsFiles.msgErrsSummary("to egrid directory"))
    egridJsFiles.errsPrint
  }
}