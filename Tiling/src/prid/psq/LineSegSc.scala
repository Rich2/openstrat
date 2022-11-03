/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

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