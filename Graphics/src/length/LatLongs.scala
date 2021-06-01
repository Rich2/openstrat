/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Immutable flat efficient Array[Double] based collection class for [[geom.LatLong]]s */
final class LatLongs(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike
{ override type ThisT = LatLongs

  override def unsafeFromArray(array: Array[Double]): LatLongs = new LatLongs(array)

  override def typeStr: String = "PolygonLL"

  def toKMs3: PolygonKMs3 = ???
  def toPolygonKms: PolygonKMs = ???
}