/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 3D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathKm2]]s on world maps. */
class LinePathKm3(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl3[PtKm3]
{ override type ThisT = LinePathKm3
  override type PolygonT = PolygonKm3
  override def typeStr: String = "LinePathKm3"
  override def ssElem(d1: Double, d2: Double, d3: Double): PtKm3 = new PtKm3(d1, d2, d3)
  override def fromArray(array: Array[Double]): LinePathKm3 = new LinePathKm3(array)
  override def polygonFromArray(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
  override def fElemStr: PtKm3 => String = _.toString
}
/** Companion object for LinePathKm3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathKm3 extends CompanionSeqLikeDbl3[PtKm3, LinePathKm3]
{ override def fromArray(array: Array[Double]): LinePathKm3 = new LinePathKm3(array)

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[LinePathKm3]]. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtKm3, LinePathKm3] = PersistSeqSpecBoth[PtKm3, LinePathKm3]("LinePathKm3")
}