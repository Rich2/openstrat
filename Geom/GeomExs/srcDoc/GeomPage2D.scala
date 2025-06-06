/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import geom.*, pWeb.*, Colour.*

/** HTML documentation for [[Polygon]]s for the [[GeomPage]]. */
object GeomPage2D extends HtmlSection
{ override def contents: RArr[XCon] = RArr(HtmlH2("Polygons"), p1)

  def p1: HtmlP = HtmlP(
    """Let us start with 2D. At is base we have "2D points and Vectors. Points can be combined to create line segments and can be use to
    | to define curves. Line segments and curves can be combined to create line paths, curve paths and shapes.""".stripMargin)  
}