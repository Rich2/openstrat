/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2D geometric element, that has a defined bounding rectangle, [[BoundingRect]]. This trait is for layout, such as placing Graphic elements in
 *  rows and columns. It includes polygon and shape graphics but not line and curve graphics. */
trait BoundedElem extends GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect

  /** The width of the [[BoundingRect]] of this object. */
  def boundingWidth: Double = boundingRect.width

  def boundingHeight: Double = boundingRect.height
  def trBounding: Pt2 = boundingRect.topRight
  def brBounding: Pt2 = boundingRect.bottomRight
  def tlBounding: Pt2 = boundingRect.topLeft
  def blBounding: Pt2 = boundingRect.bottomLeft
  @inline final def boundingCen: Pt2 = boundingRect.cen
  def cenDefault: Pt2 = boundingRect.cen
}

class BoundedExtensions[T <: BoundedElem](val thisT: T) extends AnyVal //, ev: Slate[T])
{
  /** 2D geometric translation transformation on this type T, returning an object of type T with the centre of its bounding rectangle at the parameter
   * point. */
  def slateBoundingCenTo(newCen: Pt2)(implicit ev: Slate[T]): T = ev.slateT(thisT, thisT.boundingCen >> newCen)

  /** 2D geometric translation transformation on this type T, returning an object of type T with the bottom right of its bounding rectangle at the
   *  parameter point. */
  def trSlateBoundingTo(newTopRight: Pt2)(implicit ev: Slate[T]): T = ev.slateT(thisT, thisT.trBounding >> newTopRight)
}