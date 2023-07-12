/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

object DevHtmlApp extends App
{
  val sett = findDevSettingT[DirPathAbs]("projPath")

  sett.forGoodForBad { path =>
    val path2 = path.str -/- "Dev/SbtDir/Site"
    fileWrite(path2, "index.html", IndexPage.content.out)
    appPage.all.foreach(page => fileWrite(path2, page.htmlFileName, page.out))
  } {
    errs => deb("")
    errs.foreach(println)
  }
}