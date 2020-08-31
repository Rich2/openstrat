/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package ostrat
package geom
package pExs
import Colour._, pWeb._

object SvgApp extends App 
{ 
  val cof1 = Circle(80).fill(Orange).svgInline
  val c1 = HtmlCode("Circle(80).fill(Orange).svgInline").out()
  val cof2 = Circle(120).fill(Red).svgInline
  val bodyStr =
  s"""<p>80 diameter orange Circle below. $c1</p>
    |$cof1
    |<p>120 diameter red Circle below.</p>
    |$cof2""".stripMargin
  val r = homeHtmlWrite("SvgPage1", bodyStr)
  deb(r.toString)
}
