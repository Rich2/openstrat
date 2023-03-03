/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 3D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathM]]s on world maps. */
class LinePathMetre3(val unsafeArray: Array[Double]) extends AnyVal with LinePathDbl3[PtM3]
{
  override type ThisT = LinePathMetre3
  override def ssElem(d1: Double, d2: Double, d3: Double): PtM3 = new PtM3(d1, d2, d3)
  override def fromArray(array: Array[Double]): LinePathMetre3 = new LinePathMetre3(array)
  override def typeStr: String = "LinePathM3"
  override def fElemStr: PtM3 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathMetre3 extends Dbl3SeqLikeCompanion[PtM3, LinePathMetre3]
{ override def fromArray(array: Array[Double]): LinePathMetre3 = new LinePathMetre3(array)

  implicit val persistImplicit: Dbl3SeqDefPersist[PtM3, LinePathMetre3] = new Dbl3SeqDefPersist[PtM3, LinePathMetre3]("LinePathMs3")
  { override def fromArray(value: Array[Double]): LinePathMetre3 = new LinePathMetre3(value)
  }
}