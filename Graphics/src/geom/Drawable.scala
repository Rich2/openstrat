/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Geometric entity that can be drawn producing a [[ShapeGraphic]] */
trait Drawable extends TransElem 
{
  def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): DisplayElem
}

trait Fillable extends Drawable
{
  def fill(fillColour: Colour): DisplayElem
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour = Colour.Black): DisplayElem
}