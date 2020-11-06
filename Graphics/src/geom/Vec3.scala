/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A common trait for [[Vec3]] and [[Pt3]]. Don't know if this will be useful after refactoring. */
trait Vec3Like
{ def x: Double
  def y: Double
  def z: Double
}

final class Vec3(val x: Double, val y: Double, val z: Double) extends Vec3Like
{

}

object Vec3
{ def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)
  def unapply(orig: Vec3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))

  implicit object PersistImplicit extends PersistD3[Vec3]("Vec3", "x", _.x, "y", _.y, "z", _.z, apply)
}
