/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*, pWeb.*

/** This base trait stages the HTML and CSS files for the Openstrat website, but not the JavaScript files. */
trait StagingBuild
{ /** This method stages the HTML and CSS files for the Openstrat, but not the JavaScript files. */
  def stageBase(path: DirsAbs): Unit =
  { deb(path.writeHtml(IndexPage).reportStr)
    deb(path.writeCss("only", OnlyCss()).reportStr)
    val docFiles: ErrBiAcc[IOExc, FileWritten] = stageDocDir(path)
    deb(docFiles.msgErrsSummary("to Documents directory"))

    val eGameHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = path.mkSubExist("earthgames").flatMapAcc { egDir =>
      AppPage.eGameApps.mapErrBiAcc(page => egDir.writeHtmlStem(page.fileNameStem, page))
    }
    deb(eGameHtmlFiles.msgErrsSummary("to earthgames directory"))

    val otherHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = path.mkSubExist("otherapps").flatMapAcc { otherDir =>
      AppPage.otherApps.mapErrBiAcc(page => otherDir.writeHtmlStem(page.fileNameStem, page))
    }
    deb(otherHtmlFiles.msgErrsSummary("to otherapps directory"))

    val egridHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = path.mkSubExist("egrids").flatMapAcc { egridsDir =>
      AppPage.eGrids.mapErrBiAcc(page => egridsDir.writeHtmlStem(page.fileNameStem, page))
    }
    deb(egridHtmlFiles.msgErrsSummary("to egrids directory"))
  }

  def stageDocDir(path: DirsAbs): ErrBiAcc[IOExc, FileWritten] =
  { val docPath = path.asStr / "Documentation"    
    mkDirExist(docPath).flatMapAcc { res => RArr(AppsPage, UtilPage, GeomPage, LessonsPage, TilingPage, EarthPage, EGridPage, DevPage, NewDevsPage, TomcatPage,
      ScalaOSPage, Victoria2Page).mapErrBiAcc(_.writeFile(docPath)) +% CssDocumentation.writeFile(docPath, "CssDocumentation")
    }
  }
}