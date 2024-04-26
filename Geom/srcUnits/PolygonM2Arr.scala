/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Arr of [[PolygonM2]]s. */
class PolygonM2Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonM2]
{ override type ThisT = PolygonM2Arr
  override def typeStr: String = "PolygonMArr"
  override def fElemStr: PolygonM2 => String = _.toString
  override def apply(index: Int): PolygonM2 = new PolygonM2(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonM2Arr = new PolygonM2Arr(array)
}

/** Buff of [[PolygonM2]]s. */
class PolygonM2Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonM2]
{ override type ThisT = PolygonM2Buff
  override def typeStr: String = "PolygonMBuff"
  override def fElemStr: PolygonM2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

/** Companion object of the [[PolygonM2Buff]] class, a Buff of [[PolygonM2]]s, contains factory apply method. */
object PolygonM2Buff
{ def apply(initLen: Int = 4): PolygonM2Buff = new PolygonM2Buff(new ArrayBuffer[Array[Double]](initLen))
}