/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonMs]]s on world
 *  maps. */
final class PolygonMs3(val arrayUnsafe: Array[Double]) extends AnyVal with Dbl3sArr[PtMs3]
{ override type ThisT = PolygonMs3
  override def newElem(d1: Double, d2: Double, d3: Double): PtMs3 = new PtMs3(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): PolygonMs3 = new PolygonMs3(array)
  override def typeStr: String = "PolygonMs3"
  override def fElemStr: PtMs3 => String = _.toString
  def xyPlane: PolygonMs = this.mapPolygonMs(_.xy)
}

/** Companion object for PolygonM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonMs3 extends Dbl3sArrCompanion[PtMs3, PolygonMs3]
{ override def fromArrayDbl(array: Array[Double]): PolygonMs3 = new PolygonMs3(array)

  implicit val persistImplicit: Dbl3sArrPersist[PtMs3, PolygonMs3] = new Dbl3sArrPersist[PtMs3, PolygonMs3]("PolygonMs3")
  { override def fromArray(value: Array[Double]): PolygonMs3 = new PolygonMs3(value)
  }
}