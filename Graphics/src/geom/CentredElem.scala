/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2D geometric element with a defined centre. */
trait CentredElem extends BoundedElem
{ def xCen: Double
  def yCen: Double
  def cen: Pt2

}

/** A ShapeGraphic based on a Shape with a defined centre. */
trait ShapeGraphicCentred extends ShapeGraphic with CentredElem
{ override def shape: ShapeCentred
  override def xCen: Double = shape.xCen
  override def yCen: Double = shape.yCen
  override def cen: Pt2 = shape.cen
}

/** A Shape with a centre field. */
trait ShapeCentred extends Shape with CentredElem