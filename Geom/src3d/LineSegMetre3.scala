/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment in 3 dimensional space specified in metres. A straight line between two points in 3D. */
class LineSegMetre3(xStartMs: Double, yStartMs: Double, zStartMs: Double, xEndMs: Double, yEndMs: Double, zEndMs: Double) extends
  LineSegLike[PtMetre3] with ElemDbl6
{ def typeStr: String = "LineDist3"
  //def str: String = persist2(pStart, pEnd)
  def startPt: PtMetre3 = new PtMetre3(xStartMs, yStartMs, zStartMs)
  def endPt: PtMetre3 = new PtMetre3(xEndMs, yEndMs, zEndMs)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineDist3]
  override def dbl1 = xStartMs
  override def dbl2 = yStartMs
  override def dbl3 = zStartMs
  override def dbl4 = xEndMs
  override def dbl5 = yEndMs
  override def dbl6 = zEndMs
  def xStart: Length = Length(xStartMs)
  def yStart: Length = Length(yStartMs)
  def zStart: Length = Length(zStartMs)
  def xEnd: Length = Length(xEndMs)
  def yEnd: Length = Length(yEndMs)
  def zEnd: Length = Length(zEndMs)
  def zsPos: Boolean = zStart.pos && zEnd.pos
  def toXY: LineSegMetre = new LineSegMetre(xStartMs, yStartMs, xEndMs, yEndMs)
}

/** Companion object for [[LineSegMetre3]] trait contains apply factory method. */
object LineSegMetre3
{
   def apply(pStart: PtMetre3, pEnd: PtMetre3): LineSegMetre3 = new LineSegMetre3(pStart.xMetres, pStart.yMetres, pStart.zMetres,
       pEnd.xMetres, pEnd.yMetres, pEnd.zMetres)
}