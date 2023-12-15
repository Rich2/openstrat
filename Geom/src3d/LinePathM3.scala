/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 3D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathM2]]s on world maps. */
class LinePathM3(val unsafeArray: Array[Double]) extends AnyVal with LinePathDbl3[PtM3]
{ override type ThisT = LinePathM3
  override type PolygonT = PolygonM3
  override def typeStr: String = "LinePathM3"
  override def ssElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)
  override def fromArray(array: Array[Double]): LinePathM3 = new LinePathM3(array)
  override def polygonFromArray(array: Array[Double]): PolygonM3 = new PolygonM3(array)
  override def fElemStr: PtM3 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathM3 extends CompanionSeqLikeDbl3[PtM3, LinePathM3]
{ override def fromArray(array: Array[Double]): LinePathM3 = new LinePathM3(array)

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[LinePathM3]]. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtM3, LinePathM3] = PersistSeqSpecBoth[PtM3, LinePathM3]("LinePathM3")
}