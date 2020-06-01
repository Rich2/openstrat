/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Type class for 2d geometric transformations that rotate the axes. */
trait RotateAxes[T]
{ /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
  def rotateT90(obj: T): T

  /** Rotates object of type T, 180 degrees or Pi radians. */
  def rotateT180(obj: T): T

  /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
  def rotateT270(obj: T): T
}

/** Companion object for the RotateAxes type class. */
object RotateAxes
{
  
}

/** Extension class for instances of the RotateAxes type class. */
class RotateAxesExtensions[T](value: T, ev: RotateAxes[T])
