/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import prid._, pjvm._, pWeb._

/** Object for creating openstrat documentation. */
object SiteHtmlWrite extends App
{
  deb("Starting SiteHtmlWrite")
  GridGraphic1.svgFile("Hi.svg")
  val sett = findDevSettingT[DirPathAbs]("projPath")

  sett.forGoodForBad { path =>
    val path2 = path.str / "Dev/SbtDir/Site"
    fileWrite(path2, "index.html", IndexPage.out)
    AppPage.all.foreach(page => fileWrite(path2, page.htmlFileName, page.out))
    fileWrite(path2, "apps.html", AppsPage.out)
    fileWrite(path2, "util.html", UtilPage.out)
    fileWrite(path2, "geom.html", geom.GeomPage.out)
    fileWrite(path2, "tiling.html", prid.TilingPage.out)
    fileWrite(path2, "earth.html", pEarth.EarthPage.out)
    fileWrite(path2, "egrid.html", egrid.EGridPage.out)
    fileWrite(path2, "dev.html", pDev.DevPage.out)
    fileWrite(path2, "newdevs.html", pDev.NewDevsPage.out)
  }{
    errs => deb("")
    errs.foreach(println)
  }
}