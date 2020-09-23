/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Geometric entity that can be drawn producing a [[ShapeCompound]] */
trait Drawable extends TransElem 
{
  def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): GraphicElem
}

trait Fillable extends Drawable
{
  def fill(fillColour: Colour): GraphicElem
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour = Colour.Black): GraphicElem
}