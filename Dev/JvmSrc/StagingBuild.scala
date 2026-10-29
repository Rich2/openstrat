/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*, pweb.*

/** This base trait stages the HTML and CSS files for the Openstrat website, but not the JavaScript files. */
trait StagingBuild
{ /** This method stages the HTML and CSS files for the Openstrat, but not the JavaScript files. */
  def stageBase(path: DirsAbs): Unit =
  { deb(path.writeHtml(IndexPage).reportStr)
    val cssFiles = path.writesCss(CssMain, OnlyCss)
    deb(cssFiles.reportStr)    
    val docFiles: Either[IOExc, HtmlWrittenResults] = stageDocDir(path)
    deb(docFiles.reportStr)

    val eGameHtmlFiles: Either[IOExc, HtmlWrittenResults] = path.mkSubExist("earthgames").mapResults{ egDir =>
      AppPage.eGameApps.map(page => egDir.writeHtml(page))
    }
    deb(eGameHtmlFiles.reportStr)

    val otherHtmlFiles: Either[IOExc, HtmlWrittenResults] = path.mkSubExist("otherapps").mapResults { otherDir =>
      AppPage.otherApps.map(page => otherDir.writeHtml(page))
    }
    deb(otherHtmlFiles.reportStr)

    val egridHtmlFiles: Either[IOExc, HtmlWrittenResults] = path.mkSubExist("egrids").mapResults { egridsDir =>
      AppPage.eGrids.map(page => egridsDir.writeHtml(page))
    }
    deb(egridHtmlFiles.reportStr)
  }

  def stageDocDir(path: DirsAbs): Either[IOExc, HtmlWrittenResults] =
  {
    val docPath: DirsAbs = path / "Documentation"
    docPath.mkExist.mapResults { res => RArr(AppsPage, UtilPage, GeomPage, LessonsPage, TilingPage, EarthPage, EGridPage, DevPage, NewDevsPage, TomcatPage,
      PostgresPage, ScalaOSPage, Victoria2Page).map(file => docPath.writeHtml(file))
    }
  }

  def stageDocumentationJs(projPath: DirsAbs, docPath: DirsAbs): Either[IOExc, JsWrittenResults] = docPath.mkExist.map{ res =>
    JsWrittenResults.combine("",
      jsWithMapFileRenameCopy(projPath / "out/TomcatPageJs" / "fullLinkJS.dest", docPath, "tomcat"),
      jsWithMapFileRenameCopy(projPath / "out/NewDevsPageJs" / "fullLinkJS.dest", docPath, "newdevs"),
      jsWithMapFileRenameCopy(projPath / "out/PostgresPageJs" / "fullLinkJS.dest", docPath, "postgres"),
    )
  }
}