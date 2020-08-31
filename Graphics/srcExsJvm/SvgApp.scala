/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package ostrat
package geom
package pExs
import Colour._

object SvgApp extends App 
{ 
  val cof1 = Circle(80).fill(Orange).svgInline
  val cof2 = Circle(120).fill(Red).svgInline
  val bodyStr =
  s"""<p>80 diameter orange Circle below.</p>
    |$cof1
    |<p>120 diameter red Circle below.</p>
    |$cof2""".stripMargin
  val r = homeHtmlWrite("SvgPage1", bodyStr)
  deb(r.toString)
}
