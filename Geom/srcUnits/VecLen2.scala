/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Common base trait for [[VecLen2]] and [[PtLen2]]. */
trait VecPtLen2 extends GeomLen2Elem, TellElemDbl2
{ override def name1: String = "x"
  override def name2: String = "y"

  /** The number of [[Femtometres]] in the X component of this point / vector */
  def xFemtometresNum: Double

  /** The number of [[Femtometres]] in the Y component of this point / vector */
  def yFemtometresNum: Double

  def xPicometresNum: Double
  def yPicometresNum: Double
  def xMetresNum: Double
  def yMetresNum: Double
  def xKilometresNum: Double
  def yKilometresNum: Double
  def xPos: Boolean
  def xNeg: Boolean
  def yPos: Boolean
  def yNeg: Boolean
}

trait VecLen2 extends VecPtLen2
{ def + (op: VecLen2): VecLen2
  def - (operand: VecLen2): VecLen2
  def * (operator: Double): VecLen2
  def / (operator: Double): VecLen2
  def magnitude: Length

  /** Produces the dot product of this 2-dimensional distance Vector and the operand. */
  @inline def dot(operand: VecLen2): Area
}