/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

/** Object for creating openstrat documentation. */
object SiteHtmlWrite
{
  def main(args: Array[String]): Unit =
  { deb("Starting SiteHtmlWrite")

    stagingPathDo { path =>
      debvar(path)
      fileWrite(path, "index.html", IndexPage.out)
      AppPage.all.foreach(page => fileWrite(path / page.dirStr, page.htmlFileName, page.out))
      val docPath = path / "Documentation"
      val fw = fileWrite(docPath, "apps.html", AppsPage.out)
      debvar(fw)
      fileWrite(docPath, "util.html", UtilPage.out)
      fileWrite(docPath, "geom.html", geom.GeomPage.out)
      fileWrite(docPath, "tiling.html", prid.TilingPage.out)
      fileWrite(docPath, "earth.html", pEarth.EarthPage.out)
      fileWrite(docPath, "egrid.html", EGridPage.out)
      fileWrite(docPath, "dev.html", pDev.DevPage.out)
      fileWrite(docPath, "newdevs.html", pDev.NewDevsPage.out)
      fileWrite(docPath, "documentation.css", CssDocumentation())
      fileWrite(path, "only.css", OnlyCss())
    }
  }
}