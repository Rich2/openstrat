/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in [[PtPm2]] points. The points do not have to lie within the same plane. This has been created for specifying molecular
 * structures. */
final class LinePathPm2(val arrayUnsafe: Array[Double]) extends AnyVal, LinePathDbl2[PtPm2]
{ override type ThisT = LinePathPm2
  override type PolygonT = PolygonPm2
  override def typeStr: String = "LinePathPm2"
  override def ssElem(d1: Double, d2: Double): PtPm2 = PtPm2.picometresNum(d1, d2)
  override def fromArray(array: Array[Double]): LinePathPm2 = new LinePathPm2(array)
  override def polygonFromArray(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)
  override def fElemStr: PtPm2 => String = _.toString
}

/** Companion object for [[LinePathPm2]]s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathPm2 extends CompanionSeqLikeDbl2[PtPm2, LinePathPm2]
{ override def fromArray(array: Array[Double]): LinePathPm2 = new LinePathPm2(array)

  /** Both [[Show]] and [[Unshow]] type class instances / evidence for [[LinePathPm2]] objects. */
  implicit lazy val persistEv: PersistSeqSpecBoth[PtPm2, LinePathPm2] = PersistSeqSpecBoth[PtPm2, LinePathPm2]("LinePathPm2")
}