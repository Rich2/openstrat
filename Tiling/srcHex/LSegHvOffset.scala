/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom.*, collection.mutable.ArrayBuffer

/** A Line segment where the vertices of specified in [[HvOffset]]s. */
class LSegHvOffset(val int1: Int, val int2: Int, val int3: Int, val int4: Int, val int5: Int, val int6: Int) extends LSegInt6[HvOffset]
{ override def startPt: HvOffset = new HvOffset(int1, int2, int3)
  override def endPt: HvOffset = new HvOffset(int4, int5, int6)
}

object LSegHvOffset
{ def apply(v1: HvOffset, v2: HvOffset): LSegHvOffset = new LSegHvOffset(v1.int1, v1.int2, v1.int3, v2.int1, v2.int2, v2.int3)
}

trait LineSegHvOffsetSeqLike extends Any with SeqLikeImutInt6[LSegHvOffset]

/** Specialised sequence class for [[HvOffset]]. */
class LineSegHvOffsetArr(val arrayUnsafe: Array[Int]) extends AnyVal, ArrInt6[LSegHvOffset], LineSegHvOffsetSeqLike
{ type ThisT = LineSegHvOffsetArr
  override def typeStr: String = "HvOffsetArr"
  override def fromArray(array: Array[Int]): LineSegHvOffsetArr = new LineSegHvOffsetArr(array)
  override def elemFromInts(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): LSegHvOffset = new LSegHvOffset(i1, i2, i3, i4, i5, i6)
  override def fElemStr: LSegHvOffset => String = _.toString
}

/** Specialised sequence buffer class for [[HvOffset]]. */
class LineSegHvOffsetBuff(val bufferUnsafe: ArrayBuffer[Int] = BufferInt()) extends AnyVal, BuffInt6[LSegHvOffset]
{ type ArrT = LineSegHvOffsetArr
  override def typeStr: String = "HvOffsetBuff"
  override def elemFromInts(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): LSegHvOffset = new LSegHvOffset(i1, i2, i3, i4, i5, i6)
}

object LineSegHvOffsetBuff extends CompanionBuffInt6[LSegHvOffset, LineSegHvOffsetBuff]
{ override def fromBuffer(buffer: ArrayBuffer[Int]): LineSegHvOffsetBuff = new LineSegHvOffsetBuff(buffer)
}