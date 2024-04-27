/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional point specified in units of [[Length]] rather than pure scalar numbers. */
trait PtLength2 extends VecPtLength2 with PointDbl2
{ def slate(operand: PtLength2): PtLength2
  def slateFrom(operand: PtLength2): PtLength2
  def + (operand: VecLength2): PtLength2
  def - (operand: VecLength2): PtLength2
  def addXY (otherX: Length, otherY: Length): PtLength2
  def subXY (otherX: Length, otherY: Length): PtLength2
  def addX(operand: Length): PtLength2
  def addY(operand: Length): PtLength2
  def subX(operand: Length): PtLength2
  def subY(operand: Length): PtLength2
  def * (operand: Double): PtLength2
  def / (operator: Double): PtLength2
  def divByLength(operator: Length): Pt2
  def magnitude: Length
  def revY: PtLength2
  def revYIf(cond: Boolean): PtLength2
  def lineSegTo(endPt: PtLength2): LineSegLength2[? <: PtLength2]
  def lineSegFrom(startPt: PtLength2): LineSegLength2[? <: PtLength2]

  /** Rotates the point 180 degrees around the origin by negating the X and Y components. */
  def rotate180: PtLength2

  /** Rotates th point 180 degrees around the origin if the condition is true. */
  def rotate180If(cond: Boolean): PtLength2

  /** Rotates th point 180 degrees around the origin if the condition is false. */
  def rotate180IfNot(cond: Boolean): PtLength2

  def rotate(a: AngleVec): PtLength2

  def rotateRadians(r: Double): PtLength2
}

object PtLength2
{
  implicit class Metres2Implicit(thisPtLength2: PtLength2)
  { def / (operator: Length): Pt2 = thisPtLength2.divByLength(operator)
  }
}