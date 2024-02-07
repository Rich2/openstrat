/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A Line segment where the vertices of specified in [[HvOffset]]s. */
class LineSegHvOffset(val int1: Int, val int2: Int, val int3: Int, val int4: Int, val int5: Int, val int6: Int) extends
  LineSegLikeInt6[HvOffset]
{ override def startPt: HvOffset = new HvOffset(int1, int2, int3)
  override def endPt: HvOffset = new HvOffset(int4, int5, int6)
}

object LineSegHvOffset
{ def apply(v1: HvOffset, v2: HvOffset): LineSegHvOffset = new LineSegHvOffset(v1.int1, v1.int2, v1.int3, v2.int1, v2.int2, v2.int3)
}

trait LineSegHvOffsetSeqLike extends Any with SeqLikeInt6[LineSegHvOffset]

/** Specialised sequence class for [[HvOffset]]. */
class LineSegHvOffsetArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt6[LineSegHvOffset] with LineSegHvOffsetSeqLike
{ type ThisT = LineSegHvOffsetArr
  override def typeStr: String = "HvOffsetArr"
  override def fromArray(array: Array[Int]): LineSegHvOffsetArr = new LineSegHvOffsetArr(array)

  override def newElem(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): LineSegHvOffset = new LineSegHvOffset(i1, i2, i3, i4, i5, i6)

  override def fElemStr: LineSegHvOffset => String = _.toString
}

/** Specialised sequence buffer class for [[HvOffset]]. */
class LineSegHvOffsetBuff(val unsafeBuffer: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt6[LineSegHvOffset]
{ type ArrT = LineSegHvOffsetArr
  override def typeStr: String = "HvOffsetBuff"
  override def newElem(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): LineSegHvOffset = new LineSegHvOffset(i1, i2, i3, i4, i5, i6)
}

object LineSegHvOffsetBuff extends CompanionBuffInt6[LineSegHvOffset, LineSegHvOffsetBuff]
{ override def fromBuffer(buffer: ArrayBuffer[Int]): LineSegHvOffsetBuff = new LineSegHvOffsetBuff(buffer)
}