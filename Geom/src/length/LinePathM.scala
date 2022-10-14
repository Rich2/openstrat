/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 2D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathM]]s on world maps. */
class LinePathM(val unsafeArray: Array[Double]) extends AnyVal with LinePathDbl2[PtM2]
{ override type ThisT = LinePathM
  override def ssElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
  override def unsafeFromArray(array: Array[Double]): LinePathM = new LinePathM(array)
  override def typeStr: String = "LinePathM3"
  override def fElemStr: PtM2 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathM extends Dbl2SeqLikeCompanion[PtM2, LinePathM]
{ override def fromArray(array: Array[Double]): LinePathM = new LinePathM(array)

  implicit val persistImplicit: Dbl2SeqDefPersist[PtM2, LinePathM] = new Dbl2SeqDefPersist[PtM2, LinePathM]("LinePathM")
  { override def fromArray(value: Array[Double]): LinePathM = new LinePathM(value)
  }
}