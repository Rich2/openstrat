/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._

/** A 2d line upon a HexGrid defined by its start and end [[sqGrid]] [[SqCoord]]s. */
case class LineSegSC(int1: Int, int2: Int, int3: Int, int4: Int) extends LineSegLikeInt4[SqCoord]
{ def startR: Int = int1
  def startC: Int = int2
  def endR: Int = int3
  def endC: Int = int4

  /** The start [[SqCoord]] point. */
  override def startPt: SqCoord = SqCoord(startR, startC)

  /** The end [[SqCoord]] point. */
  override def endPt: SqCoord = SqCoord(endR, endC)

  /** Method needs removing. */
  def oldLineSeg: LineSeg = LineSeg(startPt.toPt2Reg, endPt.toPt2Reg)
}

object LineSegSC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegSqC]] from the start and end hex coordinates [[SqCoord]]s. */
  def apply(scCoord1: SqCoord, scCoord2: SqCoord): LineSegSC = new LineSegSC(scCoord1.r, scCoord1.c, scCoord2.r, scCoord2.c)
}

class LineSegSCPair[A2](val a1Int1: Int, val a1Int2: Int, val a1Int3: Int, val a1Int4: Int, val a2: A2) extends ElemInt4Pair[LineSegSC, A2]
{
  //val r1: Int, val c1: Int, val r2: Int, val c2: Int

  /** The first component of this pair. */
  override def a1: LineSegSC = new LineSegSC(a1Int1, a1Int2, a1Int3, a1Int4)
}