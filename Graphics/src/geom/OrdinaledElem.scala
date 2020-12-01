/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2D geometric element where the Ordinal edge points, the cardinal edge points and the centre are defined. */
trait OrdinaledElem extends CentredElem with OrdinalEdgePoints

/** A Shape where the Ordinal edge points, the cardinal edge points and the centre are defined. This includes [[Rect]]s, [[Circle]]s, aligned ellipses
 *  and curved Rects. */
trait ShapeOrdinaled extends Shape with OrdinaledElem

/** A Shape based graphic where the Ordinal edge points, the cardinal edge points and the centre are defined. This includes [[Rect]]s, [[Circle]]s,
 *  aligned ellipses and curved Rects. */
trait ShapeGraphicOrdinaled extends ShapeGraphic with OrdinaledElem
{ override def shape: ShapeOrdinaled
  override def topRight: Pt2 = shape.topRight
  override def bottomRight: Pt2 = shape.bottomRight
  override def bottomLeft: Pt2 = shape.bottomLeft
  override def topLeft: Pt2 = shape.topLeft
}

/** Extension class for [[OrdinaledElem]]s. */
class OrdinaledExtensions[A <: OrdinaledElem](thisA: A)(implicit ev: Slate[A])
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