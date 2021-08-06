/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 3 dimensional line segment. A straight line between two points in a 3d space. */
final class LineSeg3(val startX: Double, val startY: Double, val startZ: Double, val endX: Double, val endY: Double, val endZ: Double) extends
  ElemDbl6
{ def typeStr: String = "Line3"
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineSeg3]
  override def dbl1: Double = startX
  override def dbl2 = startY
  override def dbl3 = startZ
  override def dbl4 = endX
  override def dbl5 = endY
  override def dbl6 = endZ
  def startPt: Pt3 = Pt3(startX, startY, startZ)
  def endPt: Pt3 = Pt3(endX, endY, endZ)
  def toXY: LineSeg = LineSeg(startPt.toXY, endPt.toXY)
   //def fTrans(f: Vec3 => Vec3): Line3 = Line2(f(pt1), f(pt2))
   
   //def toLatLongLine(f: Vec2 => LatLong): LatLongLine = LatLongLine(f(pt1), f(pt2))
}

/** Companion object for LineSeg3 contains factory apply methods. */
object LineSeg3
{
  def apply(pStart: Pt3, pEnd: Pt3): LineSeg3 = new LineSeg3(pStart.x, pStart.y, pStart.z, pEnd.x, pEnd.y, pEnd.z)

  def apply(xStart: Double, yStart: Double, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double): LineSeg3 =
    new LineSeg3(xStart, yStart, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double)
}