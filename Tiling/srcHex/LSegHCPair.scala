/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** [[SqCood]] defined [[LSegBase]] [[PairFinalA1Elem]]. */
class LSegHCPair[A2](val a1Int1: Int, val a1Int2: Int, val a1Int3: Int, val a1Int4: Int, val a2: A2) extends LSegLikeInt4Pair[HCoord, LSegHC, A2]
{ /** The first component of this pair. */
  override def a1: LSegHC = new LSegHC(a1Int1, a1Int2, a1Int3, a1Int4)
}

object LSegHCPair
{ def apply[A2](ls: LSegHC, a2: A2): LSegHCPair[A2] = new LSegHCPair[A2](ls.int1, ls.int2, ls.int3, ls.int4, a2)
}

class LSegHCPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends
  LSegLikeInt4PairArr[HCoord, LSegHC, LineSegHCArr, A2, LSegHCPair[A2]]
{ override type ThisT = LSegHCPairArr[A2]
  override def typeStr: String = "LineSegHCPair"
  override def a1Arr: LineSegHCArr = new LineSegHCArr(a1ArrayInt)
  override def elemFromInts(int1: Int, int2: Int, int3: Int, int4: Int, a2: A2): LSegHCPair[A2] = new LSegHCPair[A2](int1, int2, int3, int4, a2)
  override def a1FromInts(int1: Int, int2: Int, int3: Int, int4: Int): LSegHC = new LSegHC(int1, int2, int3, int4)
  override def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): LSegHCPairArr[A2] = new LSegHCPairArr[A2](newA1Array, newA2Array)
  override def fElemStr: LSegHCPair[A2] => String = _.toString
}

/** Specialised [[Buff]] class for [[LSegHCPair]]s, that uses two backing [[ArrayBuffer]]s. */
class LineSegHCPairBuff[B2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[B2]) extends BuffPairInt4[LSegHC, B2, LSegHCPair[B2]]
{ override type ThisT = LineSegHCPairBuff[B2]
  override def typeStr: String = "LineSegHCPairBuff"
  override def elemFromInts(int1: Int, int2: Int, int3: Int, int4: Int, a2: B2): LSegHCPair[B2] = new LSegHCPair[B2](int1, int2, int3, int4, a2)
}

trait LineSegHCPairArrCommonBuilder[B2] extends BuilderArrPairInt4[LSegHC, LineSegHCArr, B2, LSegHCPairArr[B2]]
{ override type BuffT = LineSegHCPairBuff[B2]
  override type B1BuffT = LineSegHCBuff
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): LineSegHCPairBuff[B2] = new LineSegHCPairBuff[B2](a1Buffer, a2Buffer)
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): LSegHCPairArr[B2] = new LSegHCPairArr[B2](b1ArrayInt, b2Array)
  override def newB1Buff(): LineSegHCBuff = LineSegHCBuff()
}

class LineSegHCPairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends LineSegHCPairArrCommonBuilder[B2],
  BuilderMapArrPairInt4[LSegHC, LineSegHCArr, B2, LSegHCPair[B2], LSegHCPairArr[B2]]
{ override def b1ArrBuilder: BuilderArrMap[LSegHC, LineSegHCArr] = LSegHC.arrMapBuilderEv
}

class LineSegHCPairArrFlatBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends LineSegHCPairArrCommonBuilder[B2],
  BuilderArrPairInt4Flat[LSegHC, LineSegHCArr, B2, LSegHCPairArr[B2]]