/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2 dimensional vector, can be used with 2 dimensional points and translations of 2 dimensional points. Thanks to Rene Descarte for this great
 *  idea. */
class Vec2(val x: Double, val y: Double) extends Vec2Like with ProdDbl2
{
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Vec2]
  @inline override def _1: Double = x
  @inline override def _2: Double = y
  override def productPrefix: String = "Vec2"

  override def equals(other: Any): Boolean = other match
  { case Vec2(px, py) => (x =~ px) && (y =~ py)
    case _ => false
  }

  /** Adds this Vector to a second 2 dimensional vector. */
  def +(operand: Vec2): Vec2 = Vec2(x + operand.x, y + operand.y)

  /** Adds the operand 2 dimensional vector from this 2 dimensional vector. */
  def -(operand: Vec2): Vec2 = Vec2(x + operand.x, y - operand.y)

  def unary_- : Vec2 = Vec2(-x, -y)
  @inline def *(factor: Double): Vec2 = Vec2(x * factor, y * factor)
  @inline def /(divisor: Double): Vec2 = Vec2(x / divisor, y / divisor)

  def yScale(factor: Double): Vec2 = Vec2(x, y * factor)
  def xScale(factor: Double): Vec2 = Vec2(x * factor, y)
  def xyScale(xOperand: Double, yOperand: Double): Vec2 = Vec2(x * xOperand, y * yOperand)

  def addXY (otherX: Double, otherY: Double): Vec2 = Vec2(x + otherX, y + otherY)
  def subXY (otherX: Double, otherY: Double): Vec2 = Vec2(x - otherX, y - otherY)

  def addX(adj: Double): Vec2 = Vec2(x + adj, y)
  def addY(adj: Double): Vec2 = Vec2(x, y + adj)
  def subX(adj: Double): Vec2 = Vec2(x - adj, y)
  def subY(adj: Double): Vec2 = Vec2(x, y - adj)

  /** rotates the vector 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotate90: Vec2 = Vec2(-y, x)

  /** Rotates the vector 180 degrees or Pi radians. */
  @inline def rotate180: Vec2 = Vec2(-x, -y)

  /** rotates the vector 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotate270: Vec2 = Vec2(y, -x)

  /** Rotates this vector through the given angle around the origin. */
  def rotate(a: Angle): Vec2 = a match
  { case Deg0 => this
    case Deg90 => rotate90
    case Deg180 => rotate180
    case Deg270 => rotate270
    case a => Vec2(x * a.cos - y * a.sin, x * a.sin + y * a.cos)
  }

  def vv(z: Double): Vec3 = Vec3(x, y, z)
}

object Vec2
{
  def apply(x: Double, y: Double): Vec2 = new Vec2(x, y)
  def unapply(orig: Vec2): Option[(Double, Double)] = Some((orig.x, orig.y))
}