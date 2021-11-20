/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 3D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathMetre]]s on world maps. */
class LinePathMetre3(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl3s[PtMetre3]
{
  override type ThisT = LinePathMetre3
  override def dataElem(d1: Double, d2: Double, d3: Double): PtMetre3 = new PtMetre3(d1, d2, d3)
  override def unsafeFromArray(array: Array[Double]): LinePathMetre3 = new LinePathMetre3(array)
  override def typeStr: String = "LinePathM3"
  override def fElemStr: PtMetre3 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathMetre3 extends DataDbl3sCompanion[PtMetre3, LinePathMetre3]
{ override def fromArrayDbl(array: Array[Double]): LinePathMetre3 = new LinePathMetre3(array)

  implicit val persistImplicit: DataDbl3sPersist[PtMetre3, LinePathMetre3] = new DataDbl3sPersist[PtMetre3, LinePathMetre3]("LinePathMs3")
  { override def fromArray(value: Array[Double]): LinePathMetre3 = new LinePathMetre3(value)
  }
}