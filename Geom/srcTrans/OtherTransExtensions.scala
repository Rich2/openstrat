/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An extensions class for types providing instances of the [[Slate]] translate and [[TransAxes]] geometric transformation type classes. */
class SlateTransAxesExtensions[A](thisReflector: A)(implicit evS: Slate[A], evR: TransAxes[A])
{
  /** Reflect, mirror across a line parallel to the X axis. */
  def reflectXOffset(yOffset: Double): A = evS.SlateYT(evR.negYT(thisReflector), 2 * yOffset)

  /** Reflect, mirror across a line parallel to the Y axis. */
  def reflectYOffset(xOffset: Double): A = evS.SlateXT(evR.negXT(thisReflector), 2 * xOffset)
}