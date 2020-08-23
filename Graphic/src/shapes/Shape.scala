/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. */
trait Shape extends TransElem
{ def fillOld(fillColour: Colour): ShapeFill
  def draw(lineWidth: Double, lineColour: Colour): ShapeDraw
  def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw

  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }
}