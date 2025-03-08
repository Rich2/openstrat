/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*

trait StagingBuild
{
  def stageBase(path: DirsAbs): Unit =
  {
    deb(htmlFileWrite(path, "index", IndexPage.out).reportStr)
    deb(cssFileWrite(path, "only", OnlyCss()).reportStr)
    val docFiles: ErrBiAcc[IOExc, FileWritten] = stageDocDir(path)
    deb(docFiles.msg2ErrsSummary("HTML", "to Documents directory"))

    val eGameHtmlFiles: ErrBiAcc[IOExc, HtmlFileWritten] = mkDirExist(path /% "earthgames").flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc(page => htmlFileWrite(path / page.dirRel, page.filesStem, page.out))
    }
    deb(eGameHtmlFiles.msgErrsSummary("to earthgames directory"))

    val otherHtmlFiles = mkDirExist(path /% "otherapps").flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc(page => fileWrite(path / page.dirRel, page.htmlFileName, page.out))
    }
    deb(otherHtmlFiles.msg2ErrsSummary("HTML", "to otherapps directory"))

    val egridHtmlFiles = mkDirExist(path /% "egrids").flatMapAcc { res =>
      AppPage.eGrids.mapErrBiAcc(page => fileWrite(path / page.dirRel, page.htmlFileName, page.out))
    }
    deb(egridHtmlFiles.msg2ErrsSummary("HTML", "to egrids directory"))
  }

  def stageDocDir(path: DirsAbs): ErrBiAcc[IOExc, FileWritten] =
  { val docPath = path /% "Documentation"
    mkDirExist(docPath).flatMapAcc { res => ErrBiAcc(
      htmlFileWrite(docPath / "apps", AppsPage.out),
      htmlFileWrite(docPath / "util", UtilPage.out),
      htmlFileWrite(docPath / "geom", GeomPage.out),
      htmlFileWrite(docPath / "tiling", TilingPage.out),
      htmlFileWrite(docPath / "earth", pEarth.EarthPage.out),
      htmlFileWrite(docPath / "egrid", EGridPage.out),
      htmlFileWrite(docPath / "dev", DevPage.out),
      htmlFileWrite(docPath / "newdevs", NewDevsPage.out),
      cssFileWrite(docPath / "documentation", CssDocumentation())
    )
    }
  }
}