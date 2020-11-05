/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait ShapeGraphicAligned extends ShapeGraphic with BoundedAligned
{ override def shape: ShapeAligned
  override def topRight: Pt2 = shape.topRight
  override def bottomRight: Pt2 = shape.bottomRight
  override def bottomLeft: Pt2 = shape.bottomLeft
  override def topLeft: Pt2 = shape.topLeft
}
