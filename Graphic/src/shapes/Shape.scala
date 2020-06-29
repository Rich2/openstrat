/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Shape extends TransElem
{ def fill(fillColour: Colour): ShapeFill
  def draw(lineWidth: Double, lineColour: Colour): ShapeDraw
  def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw
}
