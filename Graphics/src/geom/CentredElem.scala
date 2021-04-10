/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An object with a 2 dimensional scar centre. The centre is a [[Pt2]]. It has the [[Double]] properties cenX and CenY */
trait WithCentre
{ /** The X component of the centre. */
  def cenX: Double

  /** The Y component of the centre. */
  def cenY: Double

  /** The centre of this geometric / graphical element. The centre will not change if the object type is capable of being rotated and is rotated. The
   *  cenDefault on other bounded elements may move relative to points on the object when the object is rotated. */
  def cen: Pt2
}

/** A 2D geometric element with a defined centre. */
trait CentredElem extends BoundedElem with WithCentre
{
  /** The default centre of this object is the centre. The centre will not change if the object type is capable of being rotated and is rotated.*/
  override final def cenDefault: Pt2 = cen
}

/** A Shape with a centre field. */
trait ShapeCentred extends Shape with CentredElem

/** A ShapeGraphic based on a Shape with a defined centre. */
trait ShapeGraphicCentred extends ShapeGraphic with CentredElem
{ override def shape: ShapeCentred
  override def cenX: Double = shape.cenX
  override def cenY: Double = shape.cenY
  override def cen: Pt2 = shape.cen
}