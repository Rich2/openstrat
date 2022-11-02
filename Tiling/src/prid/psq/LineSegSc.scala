/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._

/** A 2d line upon a HexGrid defined by its start and end [[sqGrid]] [[SqCoord]]s. */
case class LineSegSC(r1: Int, c1: Int, r2: Int, c2: Int) extends LineSegLikeInt4[SqCoord]
{ def int1: Int = r1
  def int2: Int = c1
  def int3: Int = r2
  def int4: Int = c2

  /** The start [[SqCoord]] point. */
  def startPt: SqCoord = SqCoord(r1, c1)

  /** The end [[SqCoord]] point. */
  def endPt: SqCoord = SqCoord(r2, c2)

  /** Method needs removing. */
  def oldLineSeg: LineSeg = LineSeg(startPt.toPt2Reg, endPt.toPt2Reg)
}

object LineSegSC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegSqC]] from the start and end hex coordinates [[SqCoord]]s. */
  def apply(scCoord1: SqCoord, scCoord2: SqCoord): LineSegSC = new LineSegSC(scCoord1.r, scCoord1.c, scCoord2.r, scCoord2.c)
}

class LineSegSCPair[A2](val r1: Int, val c1: Int, val r2: Int, val c2: Int, val a2: A2) extends ElemInt4Pair[LineSegSC, A2]
{
  override def a1Int1: Int = ???

  override def a1Int2: Int = ???

  override def a1Int3: Int = ???

  override def a1Int4: Int = ???

  /** The first component of this pair. */
  override def a1: LineSegSC = ???
}