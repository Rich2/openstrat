/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait RectangleGraphic extends PolygonGraphic 
{ override def shape: Rectangle
}

trait RectangleGraphicSimple extends PolygonGraphicSimple with RectangleGraphic 

trait RectangleFill extends PolygonFill with RectangleGraphicSimple
{
  override def slate(offset: Vec2): RectangleFill = ???
}

object RectangleFill
{
  def apply(shape: Rectangle, colour: Colour): RectangleFill = ???
  
  case class RectangleFillImp(shape: Rectangle, colour: Colour) extends RectangleFill
}
