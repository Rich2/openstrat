/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 3D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathM]]s on world maps. */
class LinePathM3(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl3s[Pt3M]
{
  override type ThisT = LinePathM3
  override def dataElem(d1: Double, d2: Double, d3: Double): Pt3M = new Pt3M(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): LinePathM3 = new LinePathM3(array)
  override def typeStr: String = "LinePathM3"
  override def fElemStr: Pt3M => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathM3 extends Dbl3sDataCompanion[Pt3M, LinePathM3]
{ override def fromArrayDbl(array: Array[Double]): LinePathM3 = new LinePathM3(array)

  implicit val persistImplicit: Dbl3sDataPersist[Pt3M, LinePathM3] = new Dbl3sDataPersist[Pt3M, LinePathM3]("LinePathMs3")
  { override def fromArray(value: Array[Double]): LinePathM3 = new LinePathM3(value)
  }
}