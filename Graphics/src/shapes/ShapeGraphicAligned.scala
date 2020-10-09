/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait ShapeGraphicAligned extends ShapeGraphic with BoundedAligned
{
  override def shape: ShapeAligned
  override def xTopRight: Double = shape.xTopRight
  override def yTopRight: Double = shape.yTopRight
  override def topRight: Vec2 = shape.topRight

  override def xBottomRight: Double = ???

  override def yBottomRight: Double = ???

  override def bottomRight: Vec2 = ???

  override def xBottomLeft: Double = ???

  override def yBottomLeft: Double = ???

  override def bottomLeft: Vec2 = ???

  override def xTopLeft: Double = shape.xTopLeft

  override def yTopLeft: Double = shape.yTopLeft

  override def topLeft: Vec2 = shape.topLeft
}
