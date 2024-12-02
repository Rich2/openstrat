/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

trait StagingBuild
{
  def stageStuff(path: DirPathAbs): Unit =
  {
    fileWrite(path, "index.html", IndexPage.out)
    fileWrite(path, "only.css", OnlyCss())
    val res1: ErrBiAcc[IOExc, FileWritten] = stageDocDir(path)
    debvar(res1)
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
  { stagingPathDo { path => path.doIfDirExists(useStaging) }
  }

  def useStaging(path: DirPathAbs): Unit =
  {
    stageStuff(path)
    val res1 = ErrBiAcc(
      fileCopy("/openstrat/out/AppJs/Diceless/fullLinkJS.dest/main.js", "/CommonSsd/Staging/earthgames/dicelessapp.js"),
      fileCopy("/openstrat/out/AppJs/WW2/fullLinkJS.dest/main.js", "/CommonSsd/Staging/earthgames/ww2app.js")
    )
    debvar(res1)
  }
}