/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

object DevHtmlApp extends App
{
  val sett = findDevSettingT[String]("projPath")
  println(sett)
  sett.forGood{ path => fileWrite(path -/- "Dev/SbtDir/DicelessJs/target", "hello.html", HtmlPage.titleOnly("Hello there", "bodyContent").out) }
  println("Hello HTML")
}