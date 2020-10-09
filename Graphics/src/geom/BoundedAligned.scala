/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait BoundedAligned extends BoundedElem with AlignedElem

class BoundedAlignedExtensions[A <: BoundedAligned](thisA: A, ev: SlateTo[A])
{
  /** Translate an object of type T such that the top left of the new object is given by the new position. This method translates the object to an
   * absolute position. */
  def tlSlateTo(newTopLeft: Vec2): A = ev.slateTTo(thisA, newTopLeft - thisA.topLeftDelta)
}

trait ShapeAligned extends Shape with BoundedAligned
