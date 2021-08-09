/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A latitude-longitude polygon. A quasi polygon where the points are stored as points of latitude and longitude.Once the points are converted into a
*  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create Polygons that span an arc of greater than 90
*  degrees as this may break the algorithms. preferably keep the arcs significantly smaller. */
class PolygonLL(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike with PolygonDbl2s[LatLong]
{ type ThisT = PolygonLL
  override def unsafeFromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)
  override def typeStr: String = "PolygonLL"
  def metres3Default: PolygonMetre3 = map(_.toMetres3)
  override def toString: String = PolygonLL.persistImplicit.showT(this, Show.Standard, 2, 2)
}

/** Companion object for the [[PolygonLL]] class. */
object PolygonLL extends DataDbl2sCompanion[LatLong, PolygonLL]
{ override def fromArrayDbl(array: Array[Double]): PolygonLL = new PolygonLL(array)

  implicit val persistImplicit: DataDbl2sPersist[LatLong, PolygonLL] = new DataDbl2sPersist[LatLong, PolygonLL]("PolygonLL")
  { override def fromArray(value: Array[Double]): PolygonLL = new PolygonLL(value)
  }
}