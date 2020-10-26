/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

/** A 3 dimensional vector, can be used to represent 3 dimensional points and translations of 3 dimensional points. Right-handed coordinate
 *  system is the default */
final class Vec3 (val x: Double, val y: Double, val z: Double) extends ProdDbl3
{
  override def toString: String = Vec3.PersistImplicit.show(this, 10)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Vec3]

  def _1 = x
  def _2 = y
  def _3 = z

  override def equals(other: Any): Boolean = other match {
    case Vec3(px, py, pz) => (x =~ px) && (y =~ py) && (z =~ pz)
    case _ => false
  }

  /** The dot product of this and the operand vector. */
  @inline def dot(operand: Vec3): Double = x * operand.x + y * operand.y + z * operand.z

  def str1: String = "x: " + x.str1 + ", y: " + y.str1 + ", z: " + z.str1

  def toTriple: (Double, Double, Double) = (x, y, z)

  def +(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)

  def addXYZ(otherX: Double, otherY: Double, otherZ: Double): Vec3 = Vec3(x + otherX, y + otherY, z + otherZ)

  def toXY: Vec2 = Vec2(x, y)

  def toXYIfZPositive: Option[Vec2] = ife(z > 0, Some(Vec2(x, y)), None)

  def xRotation(rotation: Double): Vec3 =
  {
    val scalar = sqrt(y * y + z * z)
    val ang0 = ife2(
      //As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      z < 0 && y < 0, -Pi + atan(y / z),
      //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      z < 0, Pi + atan(y / z),
      //This operates on the standard atan range -Pi/2 to pi/2
      atan(y / z))

    val ang1 = ang0 + rotation
    Vec3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }
}

object Vec3
{ def apply(x: Double, y: Double, z: Double): Vec3 = new Vec3(x, y, z)
  def unapply(orig: Vec3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))
  
  implicit object PersistImplicit extends PersistD3[Vec3]("Vec3", "x", _.x, "y", _.y, "z", _.z, apply)
}