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
    val egPath: String = stagePath.asStr / "earthgames"
    val eGameJsFiles: ErrBiAcc[Exception, JsFileWritten] = mkDirExist(egPath).flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc{ ga => utiljvm.jsWithMapFileCopy(projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main", egPath / ga.fileNameStem) }
    }
    deb(eGameJsFiles.msgErrsSummary("to earthgames directory"))

    val docPath: String = stagePath.asStr / "Documentation"
    val jarApp: ErrBi[Exception, JarFileWritten] = mkDirExist(docPath).flatMap { res =>
      utiljvm.jsWithMapFileCopy(projPath.asStr / "out/DevDocJs" / "fullLinkJS.dest/main", docPath  / "tomcat")
      utiljvm.jarFileCopy(projPath.asStr / "out/DevFx/assembly.dest/out", docPath / "osapp")
    }
    deb(jarApp.reportStr)
    val otherPath: String = stagePath.asStr / "otherapps"
    val otherBi: ExcIOMon[DirExists] = mkDirExist(otherPath)
    val otherJsFiles = otherBi.flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main"
        val destStr: String = otherPath / ga.fileNameStem
        utiljvm.jsMapFileCopy(fromStr, destStr)
        utiljvm.jsFileCopy(fromStr, destStr)
      }
    }
    deb(otherJsFiles.msgErrsSummary("to otherapps directory"))

    val egridPath: String = stagePath.asStr / "egrids"
    val eGridBi: ExcIOMon[DirExists] = mkDirExist(egridPath)
    val egridJsFiles = eGridBi.flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/EGridJs" / ga.jsMainStem / "fullLinkJS.dest/main"
        val destStr: String = egridPath / ga.fileNameStem
        utiljvm.jsFileCopy(fromStr, destStr)
      }
    }
    deb(egridJsFiles.msgErrsSummary("to egrid directory"))
    egridJsFiles.errsPrint
  }
}