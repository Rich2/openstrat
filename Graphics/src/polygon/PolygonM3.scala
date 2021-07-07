/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi Polygon specified in 3D metre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[LatLongs]]s to [[PolygonM]]s on world
 *  maps. */
final class PolygonM3(val arrayUnsafe: Array[Double]) extends AnyVal with Dbl3sArr[Pt3M]
{ override type ThisT = PolygonM3
  override def newElem(d1: Double, d2: Double, d3: Double): Pt3M = new Pt3M(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
  override def typeStr: String = "PolygonMs3"
  override def fElemStr: Pt3M => String = _.toString
  def xyPlane: PolygonM = this.mapPolygonM(_.xy)
}

/** Companion object for PolygonM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object PolygonM3 extends Dbl3sArrCompanion[Pt3M, PolygonM3]
{ override def fromArrayDbl(array: Array[Double]): PolygonM3 = new PolygonM3(array)

  implicit val persistImplicit: Dbl3sArrPersist[Pt3M, PolygonM3] = new Dbl3sArrPersist[Pt3M, PolygonM3]("PolygonMs3")
  { override def fromArray(value: Array[Double]): PolygonM3 = new PolygonM3(value)
  }
}