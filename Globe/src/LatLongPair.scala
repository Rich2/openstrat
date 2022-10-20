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
}

object LatLongPairArr {

}