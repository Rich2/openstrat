/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 2D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathM2]]s on world maps. */
final class LinePathM2(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl2[PtM2]
{ override type ThisT = LinePathM2
  override type PolygonT = PolygonM2
  override def typeStr: String = "LinePathM2"
  override def ssElem(d1: Double, d2: Double): PtM2 = PtM2.metresNum(d1, d2)
  override def fromArray(array: Array[Double]): LinePathM2 = new LinePathM2(array)
  override def polygonFromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
  override def fElemStr: PtM2 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathM2 extends CompanionSeqLikeDbl2[PtM2, LinePathM2]
{ override def fromArray(array: Array[Double]): LinePathM2 = new LinePathM2(array)

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[LinePathM2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtM2, LinePathM2] = PersistSeqSpecBoth[PtM2, LinePathM2]("LinePathM2")
}