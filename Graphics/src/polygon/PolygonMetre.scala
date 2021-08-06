/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonMetre(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2s[PtMetre2] with PolygonDbl2s[PtMetre2]
{ type ThisT = PolygonMetre
  def unsafeFromArray(array: Array[Double]): PolygonMetre = new PolygonMetre(array)
  override def typeStr: String = "PolygonMs"
  override def dataElem(d1: Double, d2: Double): PtMetre2 = new PtMetre2(d1, d2)
  override def fElemStr: PtMetre2 => String = _.str
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonMetre extends DataDbl2sCompanion[PtMetre2, PolygonMetre]
{ override def fromArrayDbl(array: Array[Double]): PolygonMetre = new PolygonMetre(array)

  implicit val persistImplicit: DataDbl2sPersist[PtMetre2, PolygonMetre] = new DataDbl2sPersist[PtMetre2, PolygonMetre]("PolygonMs")
  { override def fromArray(value: Array[Double]): PolygonMetre = new PolygonMetre(value)
  }
}

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonKMs(val arrayUnsafe: Array[Double]) extends AnyVal