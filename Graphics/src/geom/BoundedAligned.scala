/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait BoundedAligned extends Centred with AlignedElem

trait ShapeAligned extends Shape with BoundedAligned

trait ShapeGraphicAligned extends ShapeGraphic with BoundedAligned
{ override def shape: ShapeAligned
  override def topRight: Pt2 = shape.topRight
  override def bottomRight: Pt2 = shape.bottomRight
  override def bottomLeft: Pt2 = shape.bottomLeft
  override def topLeft: Pt2 = shape.topLeft
}

/** A ShapeGraphic based on a Shape with centre. */
trait ShapeGraphicCentred extends ShapeGraphic with Centred
{
  override def shape: ShapeCentred

  override def xCen: Double = shape.xCen
  override def yCen: Double = shape.yCen
  override def cen: Pt2 = shape.cen
}

/** A Shape with a centre field. */
trait ShapeCentred extends Shape with Centred

class AlignedExtensions[A <: BoundedAligned](thisA: A)(implicit ev: Slate[A])
{
  /** Translate an object of type T such that the top right of the new object is given by the new position. This method translates the object to an
   * absolute position. */
  def topRightTo(newTopRight: Pt2): A = ev.slateTV(thisA, newTopRight - thisA.trOffset)

  /** Translate an object of type T such that the bottom right of the new object is given by the new position. This method translates the object to an
   * absolute position. */
  def bottomRightTo(newBottomRight: Pt2): A = ev.slateTV(thisA, newBottomRight - thisA.brOffset)

  /** Translate an object of type T such that the bottom left of the new object is given by the new position. This method translates the object to an
   * absolute position. */
  def bottomLeftTo(newBottomLeft: Pt2): A = ev.slateTV(thisA, newBottomLeft - thisA.blOffset)

  /** Translate an object of type T such that the top left of the new object is given by the new position. This method translates the object to an
   * absolute position. */
  def topLeftTo(newTopLeft: Pt2): A = ev.slateTV(thisA, newTopLeft - thisA.tlOffset)
}