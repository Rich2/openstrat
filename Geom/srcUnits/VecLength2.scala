/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait VecLength2Like extends TellElemDbl2
{
  override def name1: String = "x"
  override def name2: String = "y"
  def xMetresNum: Double
  def yMetresNum: Double
  def xKilometresNum: Double
  def yKilometresNum: Double
  def xPos: Boolean
  def xNeg: Boolean
  def yPos: Boolean
  def yNeg: Boolean
}

trait VecLength2 extends VecLength2Like
{ def + (op: VecLength2): VecLength2
  def - (operand: VecLength2): VecLength2
  def * (operator: Double): VecLength2
  def / (operator: Double): VecLength2
  def magnitude: Length

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: VecLength2): Area
}