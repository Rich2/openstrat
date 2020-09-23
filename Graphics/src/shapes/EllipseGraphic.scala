/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait EllipseGraphic extends ShapeGraphic
{ override def shape: Ellipse
  @inline final def cen: Vec2 = shape.cen
  @inline final def xCen: Double = shape.xCen
  @inline final def yCen: Double = shape.yCen
}
