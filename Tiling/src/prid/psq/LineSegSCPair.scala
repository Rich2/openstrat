/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

class LineSegSCPair[A2](val a1Int1: Int, val a1Int2: Int, val a1Int3: Int, val a1Int4: Int, val a2: A2) extends ElemInt4Pair[LineSegSC, A2]
{ /** The first component of this pair. */
  override def a1: LineSegSC = new LineSegSC(a1Int1, a1Int2, a1Int3, a1Int4)
}

class LineSegSCPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends Int4PairArr[LineSegSC, LineSegSCArr, A2, LineSegSCPair[A2]]
{ override type ThisT = LineSegSCPairArr[A2]
  override def typeStr: String = "LineSegSCPair"
  override def a1Arr: LineSegSCArr = new LineSegSCArr(a1ArrayInt)
  override def newPair(int1: Int, int2: Int, int3: Int, int4: Int, a2: A2): LineSegSCPair[A2] = new LineSegSCPair[A2](int1, int2, int3, int4, a2)

  override def newA1(int1: Int, int2: Int, int3: Int, int4: Int): LineSegSC = new LineSegSC(int1, int2, int3, int4)
  override def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): LineSegSCPairArr[A2] = new LineSegSCPairArr[A2](a1Array, a2Array)
  override def fElemStr: LineSegSCPair[A2] => String = _.toString
}

/** Specialised {{Buff]] class for [[LineSegSCPair]]s, that uses two backing [[ArrayBuffer]]s. */
class LineSegSCPairBuff[B2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[B2]) extends Int4PairBuff[LineSegSC, B2, LineSegSCPair[B2]]
{ override type ThisT = LineSegSCPairBuff[B2]
  override def typeStr: String = "LineSegSCPairBuff"
  override def newElem(int1: Int, int2: Int, int3: Int, int4: Int, a2: B2): LineSegSCPair[B2] = new LineSegSCPair[B2](int1, int2, int3, int4, a2)
}

class LineSegSCPairArrMapBuilder[B2](implicit ct: ClassTag[B2]) extends Int4PairArrMapBuilder[LineSegSC, LineSegSCArr, B2, LineSegSCPair[B2], LineSegSCPairArr[B2]]{
  override type BuffT = LineSegSCPairBuff[B2]
  override type B1BuffT = LineSegSCBuff

  override implicit def b2ClassTag: ClassTag[B2] = ct

  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): LineSegSCPairBuff[B2] = ???

  override def arrFromArrays(a1ArrayInt: Array[Int], a2Array: Array[B2]): LineSegSCPairArr[B2] = ???

  override def b1ArrBuilder: ArrMapBuilder[LineSegSC, LineSegSCArr] = LineSegSC.arrMapBuilderEv

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  override def pairArrBuilder(b1Arr: LineSegSCArr, b2s: Array[B2]): LineSegSCPairArr[B2] = ???

  /** Constructs an [[Arr]] of B from the [[Buff]]s of the B1 and B2 components. */
  override def fromBuffs(a1Buff: LineSegSCBuff, b2s: ArrayBuffer[B2]): LineSegSCPairArr[B2] = ???

  /** Constructs a new empty [[Buff]] for the B1 components of the pairs. */
  override def newB1Buff(): LineSegSCBuff = ???

  /** Expands / appends the B1 [[Buff]] with a songle element of B1. */
  override def b1BuffGrow(buff: LineSegSCBuff, newElem: LineSegSC): Unit = ???

  /** converts a the buffer type to the target compound class. */
  override def buffToBB(buff: LineSegSCPairBuff[B2]): LineSegSCPairArr[B2] = ???
}