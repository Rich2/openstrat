/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A base trait for a sequence of [[LatLong]]s. The final classes are more strongly typed as a [[LinePathLL], a [[PolygonLL]]and [[LatLongs]], for a
 * a general collection of [[LatLong]] points. */
trait LatLongsLike extends Any with Dbl2sData[LatLong]
{ final override def dataElem(d1: Double, d2: Double): LatLong = LatLong.secs(d1, d2)
  final override def fElemStr: LatLong => String = _.str
}

/** Immutable flat efficient Array[Double] based collection class for [[LatLong]]s. Prefer [[PolygonLL]] or [[LineSegLL]] where applicable. */
final class LatLongs(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike with Dbl2sSeq[LatLong]
{ override type ThisT = LatLongs
  override def unsafeFromArray(array: Array[Double]): LatLongs = new LatLongs(array)

  override def typeStr: String = "LatLongs"

  def toKMs3: PolygonM3 = ???
  def toPolygonKms: PolygonKMs = ???
}

/** A specialised flat ArrayBuffer[Double] based class for [[LatLong]]s collections. */
final class LatLongBuff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with Dbl2sBuffer[LatLong]
{ def dblsToT(d1: Double, d2: Double): LatLong = LatLong.milliSecs(d1, d2)
}