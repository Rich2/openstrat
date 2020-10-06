/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def boundingWidth: Double = boundingRect.width
  def boundingHeight: Double = boundingRect.height
  def boundingTL: Vec2 = boundingRect.topLeft
  def boundingBL: Vec2 = boundingRect.bottomLeft
}

/** BoundingRect type class */
trait BoundedTC[T]
{
  def boundingRectT(obj: T): BoundingRect
}
