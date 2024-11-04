/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import prid.*, utiljvm._, pWeb._

/** Object for creating openstrat documentation. */
object SiteHtmlWrite// extends App
{
  def main(args: Array[String]): Unit =
  { deb("Starting SiteHtmlWrite")
    //GridGraphic1.svgFile("Hi.svg")

    projPathDo { path =>
      val path1: String = path.str / "target/Site"
      fileWrite(path1, "index.html", IndexPage.out)
      AppPage.all.foreach(page => fileWrite(path1 / page.dirStr, page.htmlFileName, page.out))
      val docPath: String = path1 / "Documentation"
      fileWrite(docPath, "apps.html", AppsPage.out)
      fileWrite(docPath, "util.html", UtilPage.out)
      fileWrite(docPath, "geom.html", geom.GeomPage.out)
      fileWrite(docPath, "tiling.html", prid.TilingPage.out)
      fileWrite(docPath, "earth.html", pEarth.EarthPage.out)
      fileWrite(docPath, "egrid.html", EGridPage.out)
      fileWrite(docPath, "dev.html", pDev.DevPage.out)
      fileWrite(docPath, "newdevs.html", pDev.NewDevsPage.out)
      fileWrite(docPath, "documentation.css", CssDocumentation())
      fileWrite(path1, "only.css", OnlyCss())
    }
  }
}