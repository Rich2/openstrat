/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom.*, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A 2d line upon a HexGrid defined by its start and end [[SqGrid]] [[SqCoord]]s. */
case class LSegSC(int1: Int, int2: Int, int3: Int, int4: Int) extends LSegInt4[SqCoord]
{ def startR: Int = int1
  def startC: Int = int2
  def endR: Int = int3
  def endC: Int = int4

  /** The start [[SqCoord]] point. */
  override def startPt: SqCoord = SqCoord(startR, startC)

  /** The end [[SqCoord]] point. */
  override def endPt: SqCoord = SqCoord(endR, endC)

  /** Method needs removing. Uses the implicit [[SqGridSysFlat]] parameter to convert from [[SqCen]]s to [[Pt2]]s. */
  def oldLineSeg: LSeg2 = LSeg2(startPt.toPt2Reg, endPt.toPt2Reg)
}

/** companion object for [[LSegSC]] class contains factory apply method. */
object LSegSC
{ /** Factory apply method to create a hex coordinate line segment a [[LSegSC]] from the start and end hex coordinates [[SqCoord]]s. */
  def apply(hCoord1: SqCoord, hCoord2: SqCoord): LSegSC = new LSegSC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val arrMapBuilderEv: LineSegSCMapBuilder = new LineSegSCMapBuilder

  implicit def pairArrMapBuilderEv[B2](implicit ct: ClassTag[B2]): LineSegSCPairArrMapBuilder[B2] = new LineSegSCPairArrMapBuilder[B2]
}

/** Compact immutable Array[Int] based collection class for [[LSegSC]]s. LineSegSqC is the library's term for a mathematical straight line segment, but what
 * in common parlance is often just referred to as a line. */
class LineSegSCArr(val arrayUnsafe: Array[Int]) extends ArrInt4[LSegSC]
{ type ThisT = LineSegSCArr
  def fromArray(array: Array[Int]): LineSegSCArr = new LineSegSCArr(array)
  override def typeStr: String = "LineSegSCArr"
  override def fElemStr: LSegSC => String = _.toString
  override def elemFromInts(d1: Int, d2: Int, d3: Int, d4: Int): LSegSC = new LSegSC(d1, d2, d3, d4)
}

/** Companion object for the LineSegSqCs class. */
object LineSegSCArr extends CompanionArrInt4[LSegSC, LineSegSCArr]
{ /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  override def fromArray(array: Array[Int]): LineSegSCArr = new LineSegSCArr(array)

  override def buff(initialSize: Int): BuffInt4[LSegSC] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegSCBuff(val bufferUnsafe: ArrayBuffer[Int]) extends AnyVal, BuffInt4[LSegSC]
{ override def typeStr: String = "Line2sBuff"
  override def elemFromInts(d1: Int, d2: Int, d3: Int, d4: Int): LSegSC = new LSegSC(d1, d2, d3, d4)
}

object LineSegSCBuff
{ def apply(initLen: Int = 4): LineSegSCBuff = new LineSegSCBuff(new ArrayBuffer[Int](initLen * 4))
}

class LineSegSCMapBuilder extends BuilderMapArrInt4[LSegSC, LineSegSCArr]
{ type BuffT = LineSegSCBuff
  override def fromIntArray(array: Array[Int]): LineSegSCArr = new LineSegSCArr(array)
  def fromIntBuffer(buffer: ArrayBuffer[Int]): LineSegSCBuff = new LineSegSCBuff(buffer)
}