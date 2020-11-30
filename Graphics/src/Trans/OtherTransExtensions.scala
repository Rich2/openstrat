/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An extensions class for types providing instances of the [[Slate]] translate and [[ReflectAxes]] geometric transformation type classes. */
class SlateTransAxesExtensions[A](thisReflector: A)(implicit evS: Slate[A], evR: ReflectAxes[A])
{
  /** Reflect, mirror across a line parallel to the X axis. */
  def reflectXOffset(yOffset: Double): A = evS.ySlateT(evR.negYT(thisReflector), 2 * yOffset) 

  /** Reflect, mirror across a line parallel to the Y axis. */
  def reflectYOffset(xOffset: Double): A = evS.xSlateT(evR.negXT(thisReflector), 2 * xOffset)
}

/*
class AlignedSlateExtensions[A <: AlignedGeom](thisA: A)(implicit evS: Slate[A])
{
 // def topLeftSlate(topLeftOffset: Vec2): A = evS.slateT(thisA, topLeftOffset + 2 * thisA.cen - thisA.topLeft)
}*/
class AlignedExtensions[A <: BoundedAligned](thisA: A, ev: SlateTo[A])
{
  /** Translate an object of type T such that the top right of the new object is given by the new position. This method translates the object to an
  * absolute position.
  def trSlateTo(newTopRight: Pt2): A = ev.slateTTo(thisA, newTopRight - thisA.trOffset)

  /** Translate an object of type T such that the bottom right of the new object is given by the new position. This method translates the object to an
  * absolute position.
  def brSlateTo(newBottomRight: Pt2): A = ev.slateTTo(thisA, newBottomRight - thisA.brOffset)

  * Translate an object of type T such that the bottom left of the new object is given by the new position. This method translates the object to an
  * absolute position.
  def blSlateTo(newBottomLeft: Pt2): A = ev.slateTTo(thisA, newBottomLeft - thisA.blOffset)*/

  * Translate an object of type T such that the top left of the new object is given by the new position. This method translates the object to an
   * absolute position. */
 // def tlSlateTo(newTopLeft: Pt2): A = ev.slateTTo(thisA, newTopLeft - thisA.tlOffset)


}
