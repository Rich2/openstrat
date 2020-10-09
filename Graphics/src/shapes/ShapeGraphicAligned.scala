/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait ShapeGraphicAligned extends ShapeGraphic with BoundedAligned
{
  override def shape: ShapeAligned

  override def topRight: Vec2 = shape.topRight


  override def bottomRight: Vec2 = ???


  override def bottomLeft: Vec2 = ???


  override def topLeft: Vec2 = shape.topLeft
}
