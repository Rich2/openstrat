/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2-dimensional point specified in units of [[Length]] rather than pure scalar numbers. */
trait PtLen2 extends VecPtLen2, PointDbl2
{ def slate(operand: PtLen2): PtLen2
  def slateFrom(operand: PtLen2): PtLen2
  def + (operand: VecLen2): PtLen2
  def - (operand: VecLen2): PtLen2
  def addXY (otherX: Length, otherY: Length): PtLen2
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