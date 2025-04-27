/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An extensions class for types providing instances of the [[SlateXY]] translate and [[TransAxes]] geometric transformation type classes. */
class SlateTransAxesExtensions[A](thisReflector: A)(implicit evS: Slate2[A], evR: TransAxes[A])
{
  /** Reflect, mirror across a line parallel to the X axis. */
  def reflectXOffset(yOffset: Double): A = evS.slateY(evR.negYT(thisReflector), 2 * yOffset)

  /** Reflect, mirror across a line parallel to the Y axis. */
  def reflectYOffset(xOffset: Double): A = evS.slateX(evR.negXT(thisReflector), 2 * xOffset)
}