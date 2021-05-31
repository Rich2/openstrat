/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A base trait for a sequence of [[LatLong]]s. The final classes are more strongly typed as an [[PolygonLL]] */
trait LatLongsLike extends Any with Dbl2sArr[LatLong]
{ final override def elemBuilder(d1: Double, d2: Double): LatLong = LatLong.secs(d1, d2)
  final override def fElemStr: LatLong => String = _.str
}

/** Immutable flat efficient Array[Double] based collection class for [[geom.LatLong]]s */
class LatLongs(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike
{ type ThisT = LatLongs
  override def unsafeFromArray(array: Array[Double]): LatLongs = new LatLongs(array)
  override def typeStr: String = "LatLongs"
}

/** Companion object for the [[LatLongs]] class. */
object LatLongs extends Dbl2sArrCompanion[LatLong, LatLongs]
{
  implicit val persistImplicit: Dbl2sArrPersist[LatLong, LatLongs] = new Dbl2sArrPersist[LatLong, LatLongs]("LatLongs")
  { override def fromArray(value: Array[Double]): LatLongs = new LatLongs(value)
  }
}