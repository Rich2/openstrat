/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import geom.*, pWeb.*, Colour.*

/** HTML documentation for [[Polygon]]s for the [[GeomPage]]. */
object GeomPage2D extends HtmlSection
{ override def contents: RArr[XConElem] = RArr(HtmlH2("2D Geometry and Graphics"), HtmlP(p1), HtmlP(p2))

  def p1 = """Let us start with 2D. At is base we have "2D points and Vectors. Points can be combined to create line segments and can be use to to define curves
  |. Line segments and curves can be combined to create line paths, curve paths and shapes. Line segments, line paths, curves and curve paths can be drawn.
  | Shapes can be drawn, filled and activated, meaning that the user can interact with them using a mouse, track pad or gestures. Shapes includes polygons,
  |  mathematically simple shapes such as circles, more complex shape such as stadiums and user created shapes that consist of a sequence of curves, including
  |  line segments arcs and cubic beziers.""".stripMargin

  def p2 = """Currently there are 2 ShapeGen classes, an old one that uses an Array of Doubles and a new one that uses an Array of curveTails."""
}