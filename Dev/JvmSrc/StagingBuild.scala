/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pDoc.*

trait StagingBuild
{
  def stageBase(path: DirsAbs): Unit =
  {
    htmlFileWrite(path, "index", IndexPage.out)
    fileWrite(path, "only.css", OnlyCss())
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
      fileWrite(docPath / "apps.html", AppsPage.out),
      fileWrite(docPath / "util.html", UtilPage.out),
      fileWrite(docPath / "geom.html", GeomPage.out),
      fileWrite(docPath / "tiling.html", TilingPage.out),
      fileWrite(docPath / "earth.html", pEarth.EarthPage.out),
      fileWrite(docPath / "egrid.html", EGridPage.out),
      fileWrite(docPath / "dev.html", DevPage.out),
      fileWrite(docPath / "newdevs.html", NewDevsPage.out),
      fileWrite(docPath / "documentation.css", CssDocumentation())
    )
    }
  }
}