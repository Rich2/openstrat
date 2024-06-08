/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object WebExsApp extends App
{
  deb("WebPage1 App")
  val r = opensettHtmlWrite("Web1", TestPage.out)
  deb(r.toString)
}