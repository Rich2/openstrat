/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment in 3 dimensional space specified in metres. A straight line between two points in 3D. */
class LineSegM3(xStartMs: Double, yStartMs: Double, zStartMs: Double, xEndMs: Double, yEndMs: Double, zEndMs: Double) extends Dbl6Elem
{ def typeStr: String = "LineDist3"
  //def str: String = persist2(pStart, pEnd)
  def pStart: Pt3M = new Pt3M(xStartMs, yStartMs, zStartMs)
  def pEnd: Pt3M = new Pt3M(xEndMs, yEndMs, zEndMs)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineDist3]
  override def dbl1 = xStartMs
  override def dbl2 = yStartMs
  override def dbl3 = zStartMs
  override def dbl4 = xEndMs
  override def dbl5 = yEndMs
  override def dbl6 = zEndMs
  def xStart: Metres = Metres(xStartMs)
  def yStart: Metres = Metres(yStartMs)
  def zStart: Metres = Metres(zStartMs)
  def xEnd: Metres = Metres(xEndMs)
  def yEnd: Metres = Metres(yEndMs)
  def zEnd: Metres = Metres(zEndMs)
  def zsPos: Boolean = zStart.pos && zEnd.pos
  def toXY: LineSegDist = new LineSegDist(xStartMs, yStartMs, xEndMs, yEndMs)
}

/** Companion object for [[LineSegM3]] trait contains apply factory method. */
object LineSegM3
{
   def apply(pStart: Pt3M, pEnd: Pt3M): LineSegM3 = new LineSegM3(pStart.xMetres, pStart.yMetres, pStart.zMetres,
       pEnd.xMetres, pEnd.yMetres, pEnd.zMetres)
}