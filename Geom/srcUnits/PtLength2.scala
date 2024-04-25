/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional point specified in units of [[Length]] rather than pure scalar numbers. */
trait PtLength2 extends VecLength2Like
{ def slate(operand: PtLength2): PtLength2
  def slateFrom(operand: PtLength2): PtLength2
  def + (operand: VecLength2): PtLength2
  def - (operand: VecLength2): PtLength2
  def addXY (otherX: Length, otherY: Length): PtLength2
  def subXY (otherX: Length, otherY: Length): PtLength2
  def addX(operand: Length): PtLength2
}