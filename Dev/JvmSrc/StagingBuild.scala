/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*, pWeb.*

trait StagingBuild
{
  def stageBase(path: DirsAbs): Unit =
  { deb(htmlFileWrite(path, IndexPage).reportStr)
    deb(cssFileWrite(path, "only", OnlyCss()).reportStr)
    val docFiles: ErrBiAcc[IOExc, FileWritten] = stageDocDir(path)
    deb(docFiles.msgErrsSummary("to Documents directory"))

    val eGameHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = mkDirExist(path /% "earthgames").flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc(page => htmlFileStrWrite(path / page.dirRel, page.filesStem, page.out))
    }
    deb(eGameHtmlFiles.msgErrsSummary("to earthgames directory"))

    val otherHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = mkDirExist(path /% "otherapps").flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc(page => htmlFileStrWrite(path / page.dirRel, page.filesStem, page.out))
    }
    deb(otherHtmlFiles.msgErrsSummary("to otherapps directory"))

    val egridHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = mkDirExist(path /% "egrids").flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc(page => htmlFileStrWrite(path / page.dirRel, page.filesStem, page.out))
    }
    deb(egridHtmlFiles.msgErrsSummary("to egrids directory"))
  }

  def stageDocDir(path: DirsAbs): ErrBiAcc[IOExc, FileWritten] =
  { val docPath = path /% "Documentation"    
    mkDirExist(docPath).flatMapAcc { res => ErrBiAcc(
      htmlFileWrite(docPath, AppsPage),
      htmlFileWrite(docPath, UtilPage),
      htmlFileWrite(docPath, GeomPage),
      htmlFileWrite(docPath, LessonsPage),
      htmlFileWrite(docPath, TilingPage),
      htmlFileWrite(docPath, EarthPage),
      htmlFileWrite(docPath, EGridPage),
      htmlFileWrite(docPath, DevPage),
      htmlFileWrite(docPath, NewDevsPage),
      htmlFileWrite(docPath, TomcatPage),
      htmlFileWrite(docPath, ScalaOSPage),
      cssFileWrite(docPath / "documentation", CssDocumentation())
    )
    }
  }
}