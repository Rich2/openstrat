/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

import collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

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

class HDirnPairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends Int1PairArrMapBuilder[HDirn, HDirnArr, B2, HDirnPair[B2], HDirnPairArr[B2]]
{ override type BuffT = HDirnPairBuff[B2]
  override type B1BuffT = HDirnBuff
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): HDirnPairBuff[B2] = new HDirnPairBuff[B2](a1Buffer, a2Buffer)
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): HDirnPairArr[B2] = new HDirnPairArr[B2](b1ArrayInt, b2Array)
  override def b1ArrBuilder: ArrMapBuilder[HDirn, HDirnArr] = HDirn.arrMapBuildEv
  override def newB1Buff(): HDirnBuff = HDirnBuff()
}