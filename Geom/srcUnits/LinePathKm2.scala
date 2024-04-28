/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 2D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathKm2]]s on world maps. */
final class LinePathKm2(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl2[PtKm2]
{ override type ThisT = LinePathKm2
  override type PolygonT = PolygonKm2
  override def typeStr: String = "LinePathKm2"
  override def ssElem(d1: Double, d2: Double): PtKm2 = PtKm2.kilometresNum(d1, d2)
  override def fromArray(array: Array[Double]): LinePathKm2 = new LinePathKm2(array)
  override def polygonFromArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
  override def fElemStr: PtKm2 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathKm2 extends CompanionSeqLikeDbl2[PtKm2, LinePathKm2]
{ override def fromArray(array: Array[Double]): LinePathKm2 = new LinePathKm2(array)

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[LinePathKm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtKm2, LinePathKm2] = PersistSeqSpecBoth[PtKm2, LinePathKm2]("LinePathKm2")
}