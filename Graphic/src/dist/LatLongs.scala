package ostrat
package geom

class LatLongs(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl2[LatLong]
{ type ThisT = LatLongs
  override def unsafeFromArray(array: Array[Double]): LatLongs = new LatLongs(array)
  override def typeStr: String = "LatLongs"
  override def elemBuilder(d1: Double, d2: Double): LatLong = LatLong.degSecs(d1, d2)
}

object LatLongs extends ProdDbl2sCompanion[LatLong, LatLongs]
{
  implicit val persistImplicit: ArrProdDbl2Persist[LatLong, LatLongs] = new ArrProdDbl2Persist[LatLong, LatLongs]("LatLongs")
  { override def fromArray(value: Array[Double]): LatLongs = new LatLongs(value)
  }
}
