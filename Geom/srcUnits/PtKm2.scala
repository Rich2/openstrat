/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** A 2 dimensional point specified in [[Kilometres]] as units rather than pure scalar numbers. */
final class PtKm2(val xKilometresNum: Double, val yKilometresNum: Double) extends VecPtKm2 with PtLength2
{ override type ThisT = PtKm2
  override type LineSegT = LineSegKm2
  override def typeStr: String = ???

  override def slate(operand: PtLength2): PtLength2 = ???
  override def slateFrom(operand: PtLength2): PtLength2 = ???

  override def +(operand: VecLength2): PtLength2 = ???
  override def -(operand: VecLength2): PtLength2 = ???

  override def addXY(otherX: Length, otherY: Length): PtLength2 = ???
  override def subXY(otherX: Length, otherY: Length): PtLength2 = ???
  override def addX(operand: Length): PtLength2 = ???
  override def addY(operand: Length): PtLength2 = ???
  override def subX(operand: Length): PtLength2 = ???
  override def subY(operand: Length): PtLength2 = ???
  override def *(operand: Double): PtLength2 = ???
  override def /(operator: Double): PtLength2 = ???
  override def revY: PtLength2 = ???
  override def revYIf(cond: Boolean): PtLength2 = ???
  override def magnitude: Length = ???

  override def lineSegTo(endPt: PtKm2): LineSegKm2 = ???
  override def lineSegFrom(startPt: PtKm2): LineSegKm2 = ???


}