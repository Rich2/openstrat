/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is a graphic based on a Rect shape. A rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic with ShapeGraphicAligned
{ override def shape: Rect
}

trait RectGraphicSimple extends RectGraphic with RectangleGraphicSimple

trait RectFill extends RectGraphicSimple with RectangleFill

object RectFill
{
  def apply(rect: Rect, fillColour: Colour): RectFill = RectFillImp(rect, fillColour)
  case class RectFillImp(shape: Rect, colour: Colour) extends RectFill
}