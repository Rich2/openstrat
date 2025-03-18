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

/** A 2-dimensional point specified in units of [[Length]] rather than pure scalar numbers. */
trait PtLen2 extends VecPtLen2, PointDbl2
{ def slate(operand: PtLen2): PtLen2
  override def slate (deltaX: Length, deltaY: Length): PtLen2
  def slateFrom(operand: PtLen2): PtLen2
  def + (operand: VecLen2): PtLen2
  def - (operand: VecLen2): PtLen2
  def subXY (otherX: Length, otherY: Length): PtLen2
  def addX(operand: Length): PtLen2
  def addY(operand: Length): PtLen2
  def subX(operand: Length): PtLen2
  def subY(operand: Length): PtLen2
  def scale (operand: Double): PtLen2
  def / (operator: Double): PtLen2
  def divByLength(operator: Length): Pt2
  def magnitude: Length
  def revY: PtLen2
  def revYIf(cond: Boolean): PtLen2
  def lineSegTo(endPt: PtLen2): LineSegLen2[? <: PtLen2]
  def lineSegFrom(startPt: PtLen2): LineSegLen2[? <: PtLen2]

  //def angleTo(angle: Angle, dist: Length): PtLength2

  /** Rotates the point 180 degrees around the origin by negating the X and Y components. */
  def rotate180: PtLen2

  /** Rotates th point 180 degrees around the origin if the condition is true. */
  def rotate180If(cond: Boolean): PtLen2

  /** Rotates th point 180 degrees around the origin if the condition is false. */
  def rotate180IfNot(cond: Boolean): PtLen2

  def rotate(a: AngleVec): PtLen2

  def rotateRadians(r: Double): PtLen2
}

object PtLen2
{
  implicit class Metres2Implicit(thisPtLength2: PtLen2)
  { def / (operator: Length): Pt2 = thisPtLength2.divByLength(operator)
  }
}