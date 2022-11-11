/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** [[SqCood]] defined [[LineSegLike]] [[ElemPair]]. */
class LineSegHCPair[A2](val a1Int1: Int, val a1Int2: Int, val a1Int3: Int, val a1Int4: Int, val a2: A2) extends LineSegLikeInt4Pair[HCoord, LineSegHC, A2]
{ /** The first component of this pair. */
  override def a1: LineSegHC = new LineSegHC(a1Int1, a1Int2, a1Int3, a1Int4)
}

object LineSegHCPair{
  def apply[A2](ls: LineSegHC, a2: A2): LineSegHCPair[A2] = new LineSegHCPair[A2](ls.int1, ls.int2, ls.int3, ls.int4, a2)
}

class LineSegHCPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends LineSegLikeInt4PairArr[HCoord, LineSegHC, LineSegHCArr, A2, LineSegHCPair[A2]]
{ override type ThisT = LineSegHCPairArr[A2]
  override def typeStr: String = "LineSegHCPair"
  override def a1Arr: LineSegHCArr = new LineSegHCArr(a1ArrayInt)
  override def newPair(int1: Int, int2: Int, int3: Int, int4: Int, a2: A2): LineSegHCPair[A2] = new LineSegHCPair[A2](int1, int2, int3, int4, a2)
  override def newA1(int1: Int, int2: Int, int3: Int, int4: Int): LineSegHC = new LineSegHC(int1, int2, int3, int4)
  override def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): LineSegHCPairArr[A2] = new LineSegHCPairArr[A2](a1Array, a2Array)
  override def fElemStr: LineSegHCPair[A2] => String = _.toString
}

/** Specialised [[Buff]] class for [[LineSegHCPair]]s, that uses two backing [[ArrayBuffer]]s. */
class LineSegHCPairBuff[B2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[B2]) extends Int4PairBuff[LineSegHC, B2, LineSegHCPair[B2]]
{ override type ThisT = LineSegHCPairBuff[B2]
  override def typeStr: String = "LineSegHCPairBuff"
  override def newElem(int1: Int, int2: Int, int3: Int, int4: Int, a2: B2): LineSegHCPair[B2] = new LineSegHCPair[B2](int1, int2, int3, int4, a2)
}

trait LineSegHCPairArrCommonBuilder[B2] extends Int4PairArrCommonBuilder[LineSegHC, LineSegHCArr, B2, LineSegHCPairArr[B2]]
{ override type BuffT = LineSegHCPairBuff[B2]
  override type B1BuffT = LineSegHCBuff
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): LineSegHCPairBuff[B2] = new LineSegHCPairBuff[B2](a1Buffer, a2Buffer)
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): LineSegHCPairArr[B2] = new LineSegHCPairArr[B2](b1ArrayInt, b2Array)
  override def newB1Buff(): LineSegHCBuff = LineSegHCBuff()
}

class LineSegHCPairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends LineSegHCPairArrCommonBuilder[B2] with
Int4PairArrMapBuilder[LineSegHC, LineSegHCArr, B2, LineSegHCPair[B2], LineSegHCPairArr[B2]]
{ override def b1ArrBuilder: ArrMapBuilder[LineSegHC, LineSegHCArr] = LineSegHC.arrMapBuilderEv
}

class LineSegHCPairArrFlatBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends LineSegHCPairArrCommonBuilder[B2] with
  Int4PairArrFlatBuilder[LineSegHC, LineSegHCArr, B2, LineSegHCPairArr[B2]]