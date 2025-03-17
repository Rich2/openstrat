/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in [[PtFm2]] points. The points do not have to lie within the same plane. This has been created for specifying nucleic
 * structures. */
final class LinePathFm2(val arrayUnsafe: Array[Double]) extends AnyVal, LinePathDbl2[PtFm2]
{ override type ThisT = LinePathFm2
  override type PolygonT = PolygonFm2
  override def typeStr: String = "LinePathFm2"
  override def ssElem(d1: Double, d2: Double): PtFm2 = PtFm2(d1, d2)
  override def fromArray(array: Array[Double]): LinePathFm2 = new LinePathFm2(array)
  override def polygonFromArray(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)
  override def fElemStr: PtFm2 => String = _.toString
}

/** Companion object for [[LinePathFm2]]s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathFm2 extends CompanionSeqLikeDbl2[PtFm2, LinePathFm2]
{ override def fromArray(array: Array[Double]): LinePathFm2 = new LinePathFm2(array)

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[LinePathFm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtFm2, LinePathFm2] = PersistSeqSpecBoth[PtFm2, LinePathFm2]("LinePathFm2")
}