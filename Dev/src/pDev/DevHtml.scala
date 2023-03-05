/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._, pParse._

object DevHtmlApp extends App
{
  val sett = findDevSettingT[DirPathAbs]("projPath")

  def make(title: String): Unit = sett.forGood{path => fileWrite(path.str -/- "Dev/SbtDir", title.toLowerCase() + "app.html", HtmlPage.titleOnly(title, "bodyContent").out) }
  make("Diceless")
}