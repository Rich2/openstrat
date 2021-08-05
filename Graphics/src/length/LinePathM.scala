/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 3D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathM]]s on world maps. */
class LinePathM(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl2s[Pt2M]
{ override type ThisT = LinePathM
  override def dataElem(d1: Double, d2: Double): Pt2M = new Pt2M(d1, d2)
  override def unsafeFromArray(array: Array[Double]): LinePathM = new LinePathM(array)
  override def typeStr: String = "LinePathM3"
  override def fElemStr: Pt2M => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathM extends Dbl2sDataCompanion[Pt2M, LinePathM]
{ override def fromArrayDbl(array: Array[Double]): LinePathM = new LinePathM(array)

  implicit val persistImplicit: Dbl2sDataPersist[Pt2M, LinePathM] = new Dbl2sDataPersist[Pt2M, LinePathM]("LinePathM")
  { override def fromArray(value: Array[Double]): LinePathM = new LinePathM(value)
  }
}