/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A common trait for [[Vec3]] and [[Pt3]]. Don't know if this will be useful after refactoring. */
trait Vec3Like extends Show3Dbls
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

/** A 3 dimensional vector. This is similar data to [[Pt3]]. The purpose of this separate type is to encode the relative nature of the Vec3 as opposed
 * to the absolute nature of a point. So usually you will want and need to add the vector to an absolute point to return to the absolute realm of
 * points. Thanks to René Descartes for this great idea. */
final class Vec3(val x: Double, val y: Double, val z: Double) extends Vec3Like
{ override def typeStr: String = "Vec3"
}

object Vec3
{ def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)
  def unapply(orig: Vec3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))

  implicit object PersistImplicit extends PersistD3[Vec3]("Vec3", "x", _.x, "y", _.y, "z", _.z, apply)
}
