package ostrat
package geom

class Dist2s(val array: Array[Double]) extends AnyVal with ProductD2s[Dist2]
{ type ThisT = Dist2s
  override def unsafeFromArray(array: Array[Double]): Dist2s = new Dist2s(array)
  override def typeStr: String = "Dist2s"
  override def elemBuilder(d1: Double, d2: Double): Dist2 = new Dist2(d1, d2)
}

object Dist2s extends ProductD2sCompanion[Dist2, Dist2s]
{ implicit val factory: Int => Dist2s = i => new Dist2s(new Array[Double](i * 2))
}
