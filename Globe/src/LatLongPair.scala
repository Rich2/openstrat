/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer, reflect.ClassTag

class LatLongPair[A2](val latMilliSecs: Double, val longMilliSecs: Double, val a2: A2) extends PointDbl2Pair[LatLong, A2]
{
  override def a1: LatLong = LatLong.milliSecs(latMilliSecs, longMilliSecs)
}

class LatLongPairArr[A2](val arrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl2PairArr[LatLong, LatLongArr, A2, LatLongPair[A2]]
{ override type ThisT = LatLongPairArr[A2]

  override def a1Arr: LatLongArr = ???

  /** Accesses the defining sequence element by a 0 based index. */
  override def apply(index: Int): LatLongPair[A2] = ???

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
  override def unsafeSameSize(length: Int): LatLongPairArr[A2] = ???

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def unsafeSetElem(i: Int, value: LatLongPair[A2]): Unit = ???

  override def fElemStr: LatLongPair[A2] => String = ???

  /** String specifying the type of this object. */
  override def typeStr: String = ???
}