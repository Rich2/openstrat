/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._

/** A 3 dimensional point. Right-handed coordinate system is the default. */
final class Pt3(val x: Double, val y: Double, val z: Double) extends PointDbl3 with Vec3Like
{ override type ThisT = Pt3
  override type LineSegT = LineSeg3

  override def canEqual(other: Any): Boolean = other.isInstanceOf[Pt3]

  override def approx(that: Any, precision: Double): Boolean = that match
  { case other: Pt3 => dblsApprox(other, precision)
    case _ => false
  }

  override def equals(that: Any): Boolean = that match
  { case that: Pt3 => dblsEqual(that)
    case _ => false
  }

  /** The dot product of this and the operand vector. */
  @inline def dot(operand: Pt3): Double = x * operand.x + y * operand.y + z * operand.z

  def toTriple: (Double, Double, Double) = (x, y, z)

  def +(other: Pt3): Pt3 = Pt3(x + other.x, y + other.y, z + other.z)

  def addXYZ(otherX: Double, otherY: Double, otherZ: Double): Pt3 = Pt3(x + otherX, y + otherY, z + otherZ)

  def toXY: Pt2 = Pt2(x, y)

  def toXYIfZPositive: Option[Pt2] = ife(z > 0, Some(Pt2(x, y)), None)

  def xRotation(rotation: Double): Pt3 =
  { val scalar = sqrt(y * y + z * z)

    val ang0 = None match
    { //As y and z are both negative, the atan will give a positive value added to -Pi gives range -Pi / 2 to - Pi
      case _ if z < 0 && y < 0 => -Pi + atan(y / z)
      //The atan will give a negative value. Added to Pi gives a range Pi/2 to Pi
      case _ if z < 0 => Pi + atan(y / z)
      //This operates on the standard atan range -Pi/2 to pi/2
      case _ => atan(y / z)
    }
    val ang1 = ang0 + rotation
    Pt3(x, sin(ang1) * scalar, cos(ang1) * scalar)
  }

  /** [[LineSeg3]] from this point to the parameter point. */
  override def lineSegTo(endPt: Pt3): LineSeg3 = LineSeg3(this, endPt)

  /** [[LineSeg3]] from the parameter point to this point. */
  override def lineSegFrom(startPt: Pt3): LineSeg3 = LineSeg3(startPt, this)
}

/** Companion object for [[Pt3]] class. Contains apply, unapply factory methods and Persist type class instance. */
object Pt3
{ /** apply factory method for [[Pt3]]s. */
  def apply(x: Double, y: Double, z: Double): Pt3 = new Pt3(x, y, z)

  /** unapply extractor method for [[Pt3]]s. */
  def unapply(orig: Pt3): Option[(Double, Double, Double)] = Some((orig.x, orig.y, orig.z))

  /** Implicit [[Show]] instance / evidence for [[Pt3]]s. */
  implicit val showEv: ShowDbl3[Pt3] = ShowDbl3[Pt3]("Pt3", "x", _.x, "y", _.y, "z", _.z)

  /** Implicit [[Unshow]] instance / evidence for [[Pt3]]s. */
  implicit val unshowEv: UnshowDbl3[Pt3] = UnshowDbl3[Pt3]("Pt3", "x", "y", "z", apply)
}