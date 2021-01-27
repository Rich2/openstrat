/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait PolygonLength extends Any

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonMs(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLength with Dbl2sArr[Metres2]
{ type ThisT = PolygonMs
  def unsafeFromArray(array: Array[Double]): PolygonMs = new PolygonMs(array)
  override def typeStr: String = "PolygonMs"
  override def elemBuilder(d1: Double, d2: Double): Metres2 = new Metres2(d1, d2)
  override def fElemStr: Metres2 => String = _.str
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonMs extends Dbl2sArrCompanion[Metres2, PolygonMs]
{
  implicit val persistImplicit: Dbl2sArrPersist[Metres2, PolygonMs] = new Dbl2sArrPersist[Metres2, PolygonMs]("PolygonMs")
  { override def fromArray(value: Array[Double]): PolygonMs = new PolygonMs(value)
  }
}

/* A polygon using distances measured in metres rather than scalars. */
final class PolygonKMs(val arrayUnsafe: Array[Double]) extends AnyVal with PolygonLength