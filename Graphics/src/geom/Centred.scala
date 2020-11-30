/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Centred extends BoundedElem
{
  //def cen: Vec2
  def xCen: Double
  def yCen: Double
  def cen: Pt2

}

/*trait Aligned extends Centred
{
  def topLeft: Vec2
}*/


/** Type class for performing a 2D translation on an object of type T that moves the centre of the new object to the given position. */
trait SlateTo[T]
{
  /** Translate an object of type T such that the centre of the new object is given by the new position. */
  def slateTTo(obj: T, newCen: Pt2): T
}

class SlateToExtensions[A](thisA: A, ev: SlateTo[A])
{
  def slateTo(newCen: Pt2): A = ev.slateTTo(thisA, newCen)
}
