/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait AlignedGeom extends BoundedElem with AlignedElem
{
  def moveTo(newCen: Vec2): AlignedGeom
}

/** Type class for performing a 2D translation on an object of type T that moves the centre of the new object to the given position. */
trait SlateTo[T]
{
  /** Translate an object of type T such that the centre of the new object is given by the new position. */
  def slateTTo(obj: T, newCen: Vec2): T
}

class SlateToExtensions[A](thisA: A)(implicit evS: SlateTo[A])
{
  def slateTo(newCen: Vec2): A = evS.slateTTo(thisA, newCen)
 // def tlSlateTo(newTopLeft: Vec2): A = evS.slateTTo(thisA, newTtopLeftOffset + 2 * thisA.cen - thisA.topLeft)
}

trait ShapeAligned extends Shape with AlignedGeom
