/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*, pweb.*, webjvm.*

/** This base trait stages the HTML and CSS files for the Openstrat website, but not the JavaScript files. */
trait StagingBuild
{ /** This method stages the HTML and CSS files for the Openstrat, but not the JavaScript files. */
  def stageBase(path: DirsAbs): Unit =
  { deb(path.writeHtml(IndexPage).reportStr)
    deb(path.writeCss(OnlyCss).reportStr)
    val docFiles: ErrBiAcc[IOExc, FileWritten] = stageDocDir(path)
    deb(docFiles.msgErrsSummary("to Documents directory"))

    val eGameHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = path.mkSubExist("earthgames").mapAcc { egDir =>
      AppPage.eGameApps.mapErrBiAcc(page => egDir.writeHtml(page))
    }
    deb(eGameHtmlFiles.msgErrsSummary("to earthgames directory"))

    val otherHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = path.mkSubExist("otherapps").mapAcc { otherDir =>
      AppPage.otherApps.mapErrBiAcc(page => otherDir.writeHtml(page))
    }
    deb(otherHtmlFiles.msgErrsSummary("to otherapps directory"))

    val egridHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = path.mkSubExist("egrids").mapAcc { egridsDir =>
      AppPage.eGrids.mapErrBiAcc(page => egridsDir.writeHtml(page))
    }
    deb(egridHtmlFiles.msgErrsSummary("to egrids directory"))
  }

  def stageDocDir(path: DirsAbs): ErrBiAcc[IOExc, FileWritten] =
  {
    val docPath: DirsAbs = path / "Documentation"
    docPath.mkExist.mapAcc { res => RArr(AppsPage, UtilPage, GeomPage, LessonsPage, TilingPage, EarthPage, EGridPage, DevPage, NewDevsPage, TomcatPage,
      PostgresPage, ScalaOSPage, Victoria2Page).mapErrBiAcc(file => docPath.writeHtml(file)) +% docPath.writeCss(CssDocumentation)
    }
  }

  def stageDocumentationJs(projPath: DirsAbs, docPath: DirsAbs) = docPath.mkExist.mapAcc { res =>
    ErrBiAcc[Exception, FileWritten](
      jsWithMapFileRenameCopy(projPath / "out/TomcatPageJs" / "fullLinkJS.dest", docPath, "tomcat"),
      jsWithMapFileRenameCopy(projPath / "out/NewDevsPageJs" / "fullLinkJS.dest", docPath, "newdevs"),
      jsWithMapFileRenameCopy(projPath / "out/PostgresPageJs" / "fullLinkJS.dest", docPath, "postgres"),
      //jarFileCopy(projPath.asStr / "out/DevFx/assembly.dest/out", (docPath / "osapp").asStr) //needs improving
    )
  }
}