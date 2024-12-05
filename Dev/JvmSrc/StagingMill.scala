/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

trait StagingBuild
{
  def stageBase(path: DirPathAbs): Unit =
  {
    fileWrite(path, "index.html", IndexPage.out)
    fileWrite(path, "only.css", OnlyCss())
    val docFiles: ErrBiAcc[IOExc, FileWritten] = stageDocDir(path)
    println(docFiles.summaryStr("Documents directory HTML"))

    val eGameHtmlFiles = mkDirExist(path /> "earthgames").flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc(page => fileWrite(path / page.dirRel, page.htmlFileName, page.out))
    }
    println(eGameHtmlFiles.summaryStr("earthgames directory HTML"))

    val otherHtmlFiles = mkDirExist(path /> "otherapps").flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc(page => fileWrite(path / page.dirRel, page.htmlFileName, page.out))
    }
    println(otherHtmlFiles.summaryStr("otherapps directory HTML"))

    val egridHtmlFiles = mkDirExist(path /> "egrids").flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc(page => fileWrite(path / page.dirRel, page.htmlFileName, page.out))
    }
    println(egridHtmlFiles.summaryStr("egrids directory HTML"))
  }

  def stageDocDir(path: DirPathAbs): ErrBiAcc[IOExc, FileWritten] =
  { val docPath = path /> "Documentation"
    mkDirExist(docPath).flatMapAcc { res => ErrBiAcc(
      fileWrite(docPath / "apps.html", AppsPage.out),
      fileWrite(docPath / "util.html", UtilPage.out),
      fileWrite(docPath / "geom.html", geom.GeomPage.out),
      fileWrite(docPath / "tiling.html", pDev.TilingPage.out),
      fileWrite(docPath / "earth.html", pEarth.EarthPage.out),
      fileWrite(docPath / "egrid.html", EGridPage.out),
      fileWrite(docPath / "dev.html", pDev.DevPage.out),
      fileWrite(docPath / "newdevs.html", pDev.NewDevsPage.out),
      fileWrite(docPath / "documentation.css", CssDocumentation())
    )
    }
  }
}

object StagingMill extends StagingBuild
{
  def main(args: Array[String]): Unit =
  { stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        stageBase(stagingPath)
        useStaging(stagingPath)
      }
    }
  }

  def useStaging(stagePath: DirPathAbs): Unit = projPathDo{ projPath =>
    val egPath: String = stagePath /> "earthgames"
    val eGameJsFiles = mkDirExist(egPath).flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc(ga => fileCopy(projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js", egPath / ga.jsFileStem + ".js"))
    }
    println(eGameJsFiles.summaryStr("earthgames directory JavaScript"))

    val otherPath: String = stagePath /> "otherapps"
    val otherJsFiles = mkDirExist(otherPath).flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc { ga =>
        val fromStr: String = projPath.asStr / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js"
        val destStr: String = otherPath / ga.jsFileStem + ".js"
        fileCopy(fromStr, destStr)
      }
    }
    println(otherJsFiles.summaryStr("otherapps directory JavaScript"))
  }
}