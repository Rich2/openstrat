/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

class HDirnPair[A2](val a1Int1: Int, val a2: A2) extends Int1PairElem[HDirn, A2]
{ override def a1: HDirn = HDirn.fromInt(a1Int1)
}

class HDirnPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends Int1PairArr[HDirn, HDirnArr, A2, HDirnPair[A2]]
{ override type ThisT = HDirnPairArr[A2]
  override def typeStr: String = "HDirnPairArr"
  override def newPair(int1: Int, a2: A2): HDirnPair[A2] = new HDirnPair[A2](int1, a2)
  override def newA1(int1: Int): HDirn = HDirn.fromInt(int1)
  override def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): HDirnPairArr[A2] = new HDirnPairArr[A2](a1ArrayInt, a2Array)
  override def a1Arr: HDirnArr = new HDirnArr(a1ArrayInt)
  override def fElemStr: HDirnPair[A2] => String = _.toString
}

class HDirnPairBuff[A2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[A2]) extends Int1PairBuff[HDirn, A2, HDirnPair[A2]]
{ override type ThisT = HDirnPairBuff[A2]
  override def typeStr: String = "HDirnPairBuff"
  override def newElem(int1: Int, a2: A2): HDirnPair[A2] = new HDirnPair[A2](int1, a2)
}