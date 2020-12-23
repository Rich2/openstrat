/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2D geometric element with a defined centre. */
trait CentredElem extends BoundedElem
{ /** The X component of the centre. */
  def xCen: Double

  /** The Y component of the centre. */
  def yCen: Double

  /** The centre of this geometric / graphical element. The centre will not change if the object type is capable of being rotated and is rotated. The
   *  cenDefault on other bounded elements may move relative to points on the object when the object is rotated. */
  def cen: Pt2

  /** The default centre of this object is the centre. The centre will not change if the object type is capable of being rotated and is rotated.*/
  override final def cenDefault: Pt2 = cen
}

/** A Shape with a centre field. */
trait ShapeCentred extends Shape with CentredElem

/** A ShapeGraphic based on a Shape with a defined centre. */
trait ShapeGraphicCentred extends ShapeGraphic with CentredElem
{ override def shape: ShapeCentred
  override def xCen: Double = shape.xCen
  override def yCen: Double = shape.yCen
  override def cen: Pt2 = shape.cen
}