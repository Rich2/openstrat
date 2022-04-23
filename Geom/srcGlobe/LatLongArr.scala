/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer

/** A base trait for a sequence of [[LatLong]]s. The final classes are more strongly typed as a [[LinePathLL], a [[PolygonLL]]and [[LatLongArr]], for a
 * a general collection of [[LatLong]] points. */
trait LatLongSeqDef extends Any with Dbl2SeqDef[LatLong]
{ final override def seqDefElem(d1: Double, d2: Double): LatLong = LatLong.milliSecs(d1, d2)
  final override def fElemStr: LatLong => String = _.toString
}

/** Immutable flat efficient Array[Double] based collection class for [[LatLong]]s. Prefer [[PolygonLL]] or [[LineSegLL]] where applicable. */
final class LatLongArr(val unsafeArray: Array[Double]) extends AnyVal with LatLongSeqDef with Dbl2Arr[LatLong]
{ override type ThisT = LatLongArr
  override def unsafeFromArray(array: Array[Double]): LatLongArr = new LatLongArr(array)
  override def typeStr: String = "LatLongs"
}

/** A specialised flat ArrayBuffer[Double] based class for [[LatLong]]s collections. */
final class BuffLatLong(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl2Buff[LatLong]
{ override def typeStr: String = "LatLongBuff"
  def dblsToT(d1: Double, d2: Double): LatLong = LatLong.milliSecs(d1, d2)
}