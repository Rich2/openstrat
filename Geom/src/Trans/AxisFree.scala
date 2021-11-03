/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This is a trait for geometric elements, [[GeomElem]]s, that don't need special axes method implementations. */
trait AxisFree extends GeomElem
{ type ThisT <: AxisFree
  override def rotate(angle: AngleVec): ThisT
  override def rotate90: ThisT = rotate(Deg90)
  override def rotate180: ThisT = rotate(Deg180)
  override def rotate270: ThisT = rotate(Deg270)
  override def reflect(lineLike: LineLike): ThisT
  override def negX: ThisT = reflect(YAxis)
  override def negY: ThisT = reflect(XAxis)
}
