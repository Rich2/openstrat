/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonMetre]]s on world
 *  maps. */
final class PolygonMetre3(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl3s[PtMetre3] with PolygonDbl3s[PtMetre3]
{ override type ThisT = PolygonMetre3
  override def dataElem(d1: Double, d2: Double, d3: Double): PtMetre3 = new PtMetre3(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): PolygonMetre3 = new PolygonMetre3(array)
  override def typeStr: String = "PolygonM3"
  override def fElemStr: PtMetre3 => String = _.toString
  def xyPlane: PolygonMetre = this.mapPolygon(_.xy)
}

/** Companion object for PolygonM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonMetre3 extends DataDbl3sCompanion[PtMetre3, PolygonMetre3]
{ override def fromArrayDbl(array: Array[Double]): PolygonMetre3 = new PolygonMetre3(array)

  implicit val persistImplicit: DataDbl3sPersist[PtMetre3, PolygonMetre3] = new DataDbl3sPersist[PtMetre3, PolygonMetre3]("PolygonMs3")
  { override def fromArray(value: Array[Double]): PolygonMetre3 = new PolygonMetre3(value)
  }
}