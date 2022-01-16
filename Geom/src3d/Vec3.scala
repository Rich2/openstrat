/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 3 dimensional vector. This is similar data to [[Pt3]]. The purpose of this separate type is to encode the relative nature of the Vec3 as opposed
 * to the absolute nature of a point. So usually you will want and need to add the vector to an absolute point to return to the absolute realm of
 * points. Thanks to René Descartes for this great idea. */
final class Vec3(val x: Double, val y: Double, val z: Double) extends Vec3Like
{ override def typeStr: String = "Vec3"
  override def canEqual(that: Any): Boolean = isInstanceOf[Vec3]

  override def approx(that: Any, precision: Double): Boolean = that match
  { case other: Vec3 => dblsApprox(other, precision)
    case _ => false
  }

  override def equals(that: Any): Boolean = that match
  { case that: Vec3 => dblsEqual(that)
    case _ => false
  }
}

/** Companion object for 3 dimensional vector [[Vec3]] class. Contains apply and unapply factory methods, and Persist type class instance. */
object Vec3
{ def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)
  def unapply(orig: Vec3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))

 implicit val persistImplicit: ShowShowDbl3T[Vec3] = ShowShowDbl3T[Vec3]("Vec3", "x", "y", "z")//, apply)
}