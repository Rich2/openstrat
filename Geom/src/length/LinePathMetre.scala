/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A quasi line path specified in 2D metre points. The points do not have to lie within the same plane. I'm not sure how useful this class will
 *  prove. It has been created for the intermediary step of converting from [[LinePathLL]]s to [[LinePathMetre]]s on world maps. */
class LinePathMetre(val arrayUnsafe: Array[Double]) extends AnyVal with LinePathDbl2s[PtM2]
{ override type ThisT = LinePathMetre
  override def dataElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
  override def unsafeFromArray(array: Array[Double]): LinePathMetre = new LinePathMetre(array)
  override def typeStr: String = "LinePathM3"
  override def fElemStr: PtM2 => String = _.toString
}
/** Companion object for LinePathM3s. Contains apply factory method fromArrayDbl and Persist Implicit. */
object LinePathMetre extends DataDbl2sCompanion[PtM2, LinePathMetre]
{ override def fromArrayDbl(array: Array[Double]): LinePathMetre = new LinePathMetre(array)

  implicit val persistImplicit: DataDbl2sPersist[PtM2, LinePathMetre] = new DataDbl2sPersist[PtM2, LinePathMetre]("LinePathM")
  { override def fromArray(value: Array[Double]): LinePathMetre = new LinePathMetre(value)
  }
}