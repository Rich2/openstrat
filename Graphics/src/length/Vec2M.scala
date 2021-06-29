/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional vector specified in metres as units rather than pure scalar numbers. */
final class Vec2M private (val xMetresNum: Double, val yMetresNum: Double) extends Show2Dbls
{ override def typeStr: String = "Vec2M"

  /** The X component of this 2 dimensional [[Metres]] vector. */
  def x: Metres = Metres(xMetresNum)

  /** The Y component of this 2 dimensional [[Metres]] vector. */
  def y: Metres = Metres(yMetresNum)

  /** the name of the 1st element of this 2 element product. */
  override def name1: String = "x"

  /** the name of the 2nd element of this 2 element product. */
  override def name2: String = "y"

  /** Element 1 of this Show 2 element product. */
  override def show1: Double = ???

  /** Element 2 of this Show 2 element product. */
  override def show2: Double = ???
  def * (operator: Double): Vec2M = new Vec2M(xMetresNum * operator, yMetresNum * operator)
  def / (operator: Double): Vec2M = new Vec2M(xMetresNum / operator, yMetresNum / operator)
  def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))
  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Vec2M): Area = x * operand.x + y * operand.y
}