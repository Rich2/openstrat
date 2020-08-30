/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package ostrat
package geom
package pExs
import Colour._

object SvgApp extends App 
{ 
  val cof = Circle(80).fill(Orange).viewPort
  val bodyStr =
  s"""<p>Orange Circle</p>
    |$cof""".stripMargin
  val r = homeHtmlWrite("SvgPage1", bodyStr)
  deb(r.toString)
  deb("Hello")
}
