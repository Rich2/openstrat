/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import prid._, utiljvm._, pWeb._

/** Object for creating openstrat documentation. */
object SiteHtmlWrite extends App
{ deb("Starting SiteHtmlWrite")
  GridGraphic1.svgFile("Hi.svg")

  projPathDo { path =>
    val path1: String = path.str / "target/Site"
    fileWriteOld(path1, "index.html", IndexPage.out)
    AppPage.all.foreach(page => fileWriteOld(path1 / page.dirStr, page.htmlFileName, page.out))
    val docPath: String = path1 / "Documentation"
    fileWriteOld(docPath, "apps.html", AppsPage.out)
    fileWriteOld(docPath, "util.html", UtilPage.out)
    fileWriteOld(docPath, "geom.html", geom.GeomPage.out)
    fileWriteOld(docPath, "tiling.html", prid.TilingPage.out)
    fileWriteOld(docPath, "earth.html", pEarth.EarthPage.out)
    fileWriteOld(docPath, "egrid.html", EGridPage.out)
    fileWriteOld(docPath, "dev.html", pDev.DevPage.out)
    fileWriteOld(docPath, "newdevs.html", pDev.NewDevsPage.out)
    fileWriteOld(docPath, "documentation.css", CssDocumentation())
    fileWriteOld(path1, "only.css", OnlyCss())
  }
}