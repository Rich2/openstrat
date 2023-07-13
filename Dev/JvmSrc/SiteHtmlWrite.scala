/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._

object SiteHtmlWrite extends App
{
  val sett = findDevSettingT[DirPathAbs]("projPath")

  sett.forGoodForBad { path =>
    val path2 = path.str -/- "Dev/SbtDir/Site"
    fileWrite(path2, "index.html", IndexPage.out)
    AppPage.all.foreach(page => fileWrite(path2, page.htmlFileName, page.out))
    fileWrite(path2, "apps.html", AppsDoc.out)
    fileWrite(path2, "Util.html", UtilPage.out)
  }{
    errs => deb("")
    errs.foreach(println)
  }
}