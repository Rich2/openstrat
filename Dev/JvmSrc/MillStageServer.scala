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
    val eGameJsFiles: ErrBiAcc[Exception, JsFileWritten] = mkDirExist(egPath).flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc{ ga => jsWithMapFileCopy(projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main", egPath / ga.fileNameStem) }
    }
    deb(eGameJsFiles.msgErrsSummary("to earthgames directory"))

    val docPath: String = stagePath /% "Documentation"
    val jarApp: ErrBi[Exception, JarFileWritten] = mkDirExist(docPath).flatMap { res =>
      jsMapFileCopy(projPath.asStr / "out/DevDocJs" / "fullLinkJS.dest/main", docPath  / "tomcat")
      jsFileCopy(projPath.asStr / "out/DevDocJs" / "fullLinkJS.dest/main", docPath  / "tomcat")
      jarFileCopy(projPath.asStr / "out/DevFx/assembly.dest/out", docPath / "osapp")
    }
    deb(jarApp.reportStr)
    val otherPath: String = stagePath /% "otherapps"
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

    val egridPath: String = stagePath /% "egrids"
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