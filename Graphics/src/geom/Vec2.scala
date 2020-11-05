/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

class Vec2(val x: Double, val y: Double) extends Vec2Like with ProdDbl2
{
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Vec2]
  @inline override def _1: Double = x
  @inline override def _2: Double = y
  override def productPrefix: String = "Pt2"

  /** Adds this Vector to a second 2 dimensional vector. */
  def +(operand: Vec2): Vec2 = Vec2(x + operand.x, y + operand.y)
}

object Vec2
{
  def apply(x: Double, y: Double): Vec2 = new Vec2(x, y)
  def unapply(orig: Pt2): Option[(Double, Double)] = Some((orig.x, orig.y))
}