/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._, pParse._

object DevHtmlApp extends App
{
  val sett = findDevSettingT[String]("projPath")

  def make(title: String): Unit = sett.forGood{path => fileWrite(path -/- "Dev/SbtDir", title.toLowerCase() + "app.html", HtmlPage.titleOnly(title, "bodyContent").out) }
  //make("Diceless")

  val res = "/Sudo/hello".parseExpr//.asType[DirPathAbs]
  println(res)
}