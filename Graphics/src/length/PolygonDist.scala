/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonDist(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl2[Metres2]
{ type ThisT = PolygonDist
  def unsafeFromArray(array: Array[Double]): PolygonDist = new PolygonDist(array)
  override def typeStr: String = "DPolygon"
  override def elemBuilder(d1: Double, d2: Double): Metres2 = new Metres2(d1, d2)
  override def fElemStr: Metres2 => String = _.str
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonDist extends ProdDbl2sCompanion[Metres2, PolygonDist]
{
  implicit val persistImplicit: ArrProdDbl2Persist[Metres2, PolygonDist] = new ArrProdDbl2Persist[Metres2, PolygonDist]("DPolygon")
  { override def fromArray(value: Array[Double]): PolygonDist = new PolygonDist(value)
  }
}