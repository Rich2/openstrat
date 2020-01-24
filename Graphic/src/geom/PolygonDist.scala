package ostrat
package geom

/* A polygon using distances. */
class PolygonDist(val array: Array[Double]) extends AnyVal with ArrProdDbl2[Dist2]
{ type ThisT = PolygonDist
  def unsafeFromArray(array: Array[Double]): PolygonDist = new PolygonDist(array)
  override def typeStr: String = "DPolygon"
  override def elemBuilder(d1: Double, d2: Double): Dist2 = new Dist2(d1, d2)
}

/** The companion object for DPolygon. Provides an implicit builder. */
object PolygonDist extends ProdDbl2sCompanion[Dist2, PolygonDist]
{
  implicit val persistImplicit: ArrProdDbl2Persist[Dist2, PolygonDist] = new ArrProdDbl2Persist[Dist2, PolygonDist]("DPolygon")
  { override def fromArray(value: Array[Double]): PolygonDist = new PolygonDist(value)
  }
}