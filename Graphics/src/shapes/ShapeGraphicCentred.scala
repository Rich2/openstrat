/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait ShapeGraphicCentred extends ShapeGraphic with Centred
{
  override def shape: ShapeCentred

  override def xCen: Double = shape.xCen
  override def yCen: Double = shape.yCen
  override def cen: Pt2 = shape.cen
}
