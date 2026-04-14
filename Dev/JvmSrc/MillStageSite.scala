/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*

object MillTomDocStage
{
  def main(args: Array[String]): Unit =
  { deb("Starting MillTomDocstage")
    val res = projPathFind.flatMap { projPath =>
      stagingPathFind.flatMap { stagingPath1 =>
        stagingPath1.mkExist.flatMap { _ =>
          val stagingPath2: DirsAbs = stagingPath1 / "OpenstratSite"
          stagingPath2.mkExist.flatMap { _ => jsWithMapFileCopy(projPath.outFullLink("DevDocJs"), stagingPath2 / "Documentation" :-/ "tomcat") }
        }
      }
    }
    debvar(res)
  }
}

/** application used by mill to stage openstrat files for a passive server. */
object MillStageSite extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath1 =>
      stagingPath1.doIfDirExists { _ =>
        val stagingPath2 = stagingPath1 / "OpenstratSite"
        stagingPath2.mkExist
        stageBase(stagingPath2)
        useStaging(stagingPath2)
      }
    }
  }

  def useStaging(stagePath: DirsAbs): Unit = projPathDo{ projPath =>
    val egPath: String = stagePath.asStr / "earthgames"
    val eGameJsFiles: ErrBiAcc[Exception, JsFileWritten] = mkDirExist(egPath).flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc{ ga => jsWithMapFileCopy(projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main", egPath / ga.fileNameStem) }
    }
    deb(eGameJsFiles.msgErrsSummary("to earthgames directory"))

    val docPath: String = stagePath.asStr / "Documentation"
    val jarApp: ErrBi[Exception, JarFileWritten] = mkDirExist(docPath).flatMap { res =>
      jsWithMapFileCopy(projPath.asStr / "out/DevDocJs" / "fullLinkJS.dest/main", docPath  / "tomcat")
      //jsFileCopy(projPath.asStr / "out/DevDocJs" / "fullLinkJS.dest/main", docPath  / "tomcat")
      jarFileCopy(projPath.asStr / "out/DevFx/assembly.dest/out", docPath / "osapp")
    }
    deb(jarApp.reportStr)
    val otherPath: String = stagePath.asStr / "otherapps"
    val otherBi: ExcIOMon[DirExists] = mkDirExist(otherPath)
    val otherJsFiles = otherBi.flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main"
        val destStr: String = otherPath / ga.fileNameStem
        jsMapFileCopy(fromStr, destStr)
        jsFileCopy(fromStr, destStr)
      }
    }
    deb(otherJsFiles.msgErrsSummary("to otherapps directory"))

    val egridPath: String = stagePath.asStr / "egrids"
    val eGridBi: ExcIOMon[DirExists] = mkDirExist(egridPath)
    val egridJsFiles = eGridBi.flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest/main"
        val destStr: String = egridPath / ga.fileNameStem
        jsFileCopy(fromStr, destStr)
      }
    }
    deb(egridJsFiles.msgErrsSummary("to egrid directory"))
    egridJsFiles.errsPrint
  }
}