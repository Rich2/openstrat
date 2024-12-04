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
    println(docFiles.summaryStr("Documents directory"))
    AppPage.all.foreach(page => fileWrite(path / page.dirStr, page.htmlFileName, page.out))
  }

  def stageDocDir(path: DirPathAbs): ErrBiAcc[IOExc, FileWritten] =
  { val docPath = path / "Documentation"
    mkDirExist(docPath).flatMapAcc { res => ErrBiAcc(
      fileWrite(docPath, "apps.html", AppsPage.out),
      fileWrite(docPath, "util.html", UtilPage.out),
      fileWrite(docPath, "geom.html", geom.GeomPage.out),
      fileWrite(docPath, "tiling.html", prid.TilingPage.out),
      fileWrite(docPath, "earth.html", pEarth.EarthPage.out),
      fileWrite(docPath, "egrid.html", EGridPage.out),
      fileWrite(docPath, "dev.html", pDev.DevPage.out),
      fileWrite(docPath, "newdevs.html", pDev.NewDevsPage.out),
      fileWrite(docPath, "documentation.css", CssDocumentation())
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
    val egPath = stagePath / "earthgames"
    val eGameJsFiles = mkDirExist(egPath).flatMapAcc { res =>
      AppPage.eGameApps.mapErrBiAcc(ga => fileCopy(projPath.str / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js", egPath.str / ga.jsFileStem + ".js"))
    }
    println(eGameJsFiles.summaryStr("earthgames directory"))

    val otherPath = stagePath / "otherapps"
    val otherJsFiles = mkDirExist(otherPath).flatMapAcc { res =>
      AppPage.otherApps.mapErrBiAcc(ga => fileCopy(projPath.str / "out/AppJs" / ga.jsMainStem / "fullLinkJS.dest/main.js", otherPath.str / ga.jsFileStem + ".js"))
    }
    println(otherJsFiles.summaryStr("otherapps directory"))
  }
}