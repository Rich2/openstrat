/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A common trait for [[Vec3]] and [[Pt3]]. Don't know if this will be useful after refactoring. */
trait Vec3Like extends Show3Dbls with ApproxDbl
{ def x: Double
  def y: Double
  def z: Double

  /** the name of the 1st element of this 3 element Show product. */
  override def name1: String = "x"

  /** the name of the 2nd element of this 3 element Show product. */
  override def name2: String = "y"

  /** the name of the 3rd element of this 3 element Show product. */
  override def name3: String = "z"

  /** Element 1 of this 3 element Show product. */
  override def show1: Double = x

  /** Element 2 of this 3 element Show product. */
  override def show2: Double = y

  /** Element 3 of this 3 element Show product. */
  override def show3: Double = z
}