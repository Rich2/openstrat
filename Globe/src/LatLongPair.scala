/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer, reflect.ClassTag

class LatLongPair[A2](val latMilliSecs: Double, val longMilliSecs: Double, val a2: A2) extends PointDbl2Pair[LatLong, A2]
{ override def a1Dbl1: Double = latMilliSecs
  override def a1Dbl2: Double = longMilliSecs
  override def a1: LatLong = LatLong.milliSecs(latMilliSecs, longMilliSecs)
}

class LatLongPairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl2PairArr[LatLong, LatLongArr, A2, LatLongPair[A2]]
{ override type ThisT = LatLongPairArr[A2]
  override def typeStr: String = "LatLongPairArr"
  override def a1Arr: LatLongArr = new LatLongArr(a1ArrayDbl)

  override def newPair(dbl1: Double, dbl2: Double, a2: A2): LatLongPair[A2] = new LatLongPair[A2](dbl1, dbl2, a2)

  override def fElemStr: LatLongPair[A2] => String = _.toString

  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): LatLongPairArr[A2] = new LatLongPairArr[A2](a1ArrayDbl, a2Array)

  override def newA1(dbl1: Double, dbl2: Double): LatLong = LatLong.milliSecs(dbl1, dbl2)
}

object LatLongPairArr extends Dbl2PairArrCompanion [LatLong, LatLongArr]{
  def apply[A2](pairs: LatLongPair[A2]*)(implicit ct: ClassTag[A2]): LatLongPairArr[A2] =
  { val arrays = seqToArrays(pairs)
    new LatLongPairArr[A2](arrays._1, arrays._2)
  }
}