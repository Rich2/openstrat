package ostrat
package geom

class LatLongs(val array: Array[Double]) extends AnyVal with ArrProdDbl2[LatLong]
{ type ThisT = LatLongs
  override def unsafeFromArray(array: Array[Double]): LatLongs = new LatLongs(array)
  override def typeStr: String = "LatLongs"
  override def elemBuilder(d1: Double, d2: Double): LatLong = LatLong.apply(d1, d2)
}

object LatLongs extends ProductD2sCompanion[LatLong, LatLongs]
{
  implicit val factory: Int => LatLongs = i => new LatLongs(new Array[Double](i * 2))
  //override def fromArray(value: Array[Double]): LatLongs = new LatLongs(value)
}
