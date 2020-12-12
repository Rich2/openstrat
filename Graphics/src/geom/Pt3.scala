/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

/** A 3 dimensional point. Right-handed coordinate system is the default. */
final class Pt3(val x: Double, val y: Double, val z: Double) extends Vec3Like with ProdDbl3
{
  override def toString: String = Pt3.PersistImplicit.show(this, 10)
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Pt3]

  def _1 = x
  def _2 = y
  def _3 = z

  override def equals(other: Any): Boolean = other match {
    case Pt3(px, py, pz) => (x =~ px) && (y =~ py) && (z =~ pz)
    case _ => false
  }

  /** The dot product of this and the operand vector. */
  @inline def dot(operand: Pt3): Double = x * operand.x + y * operand.y + z * operand.z

  def str1: String = "x: " + x.str1 + ", y: " + y.str1 + ", z: " + z.str1

  def toTriple: (Double, Double, Double) = (x, y, z)

  def +(other: Pt3): Pt3 = Pt3(x + other.x, y + other.y, z + other.z)

  def addXYZ(otherX: Double, otherY: Double, otherZ: Double): Pt3 = Pt3(x + otherX, y + otherY, z + otherZ)

  def toXY: Pt2 = Pt2(x, y)

  def toXYIfZPositive: Option[Pt2] = ife(z > 0, Some(Pt2(x, y)), None)

  def xRotation(rotation: Double): Pt3 =
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
    Pt3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }
}

object Pt3
{ def apply(x: Double, y: Double, z: Double): Pt3 = new Pt3(x, y, z)
  def unapply(orig: Pt3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))

  implicit object PersistImplicit extends PersistD3[Pt3]("Pt3", "x", _.x, "y", _.y, "z", _.z, apply)
}