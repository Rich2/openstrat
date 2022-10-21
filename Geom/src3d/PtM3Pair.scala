/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import scala.collection.mutable.ArrayBuffer

class PtM3Pair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a2: A2) extends PointDbl3Pair[PtM3, A2]
{ override def a1: PtM3 = new PtM3(a1Dbl1, a1Dbl2, a1Dbl3)
}

class PtM3PairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl3PairArr[PtM3, PtM3Arr, A2, PtM3Pair[A2]]
{ override type ThisT = PtM3PairArr[A2]
  override def typeStr: String = "PtM3PairArr"
  override def newPair(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): PtM3Pair[A2] = new PtM3Pair[A2](dbl1, dbl2, dbl3, a2)
  override def a1Arr: PtM3Arr = new PtM3Arr(a1ArrayDbl)
  override def fElemStr: PtM3Pair[A2] => String = _.toString
}

class PtM3PairBuff[A2](val a1DblBuffer: ArrayBuffer[Double], val a2Buffer: ArrayBuffer[A2]) extends Dbl3PairBuff[PtM3, A2, PtM3Pair[A2]]
{ override type ThisT = PtM3PairBuff[A2]
  override def typeStr: String = "PtM3PairBuff"
  override def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): PtM3Pair[A2] = new PtM3Pair[A2](dbl1, dbl2, dbl3, a2)
}

class PtM3PairArrBuider[A2] extends Dbl3PairArrBuilder[PtM3, PtM3Arr, A2, PtM3Pair[A2], PtM3PairArr[A2]]
{
  override type BuffT = PtM3PairBuff[A2]

  override def b1ArrBuilder: ArrBuilder[PtM3, PtM3Arr] = PtM3.builderImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(b1Arr: PtM3Arr, a2s: Array[A2]): PtM3PairArr[A2] = new PtM3PairArr[A2](b1Arr.unsafeArray, a2s)

  override def newArr(length: Int): PtM3PairArr[A2] = ???

  override def arrSet(arr: PtM3PairArr[A2], index: Int, value: PtM3Pair[A2]): Unit = ???

  override def newBuff(length: Int): PtM3PairBuff[A2] = ???

  /** converts a the buffer type to the target compound class. */
  override def buffToBB(buff: PtM3PairBuff[A2]): PtM3PairArr[A2] = ???
}