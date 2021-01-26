/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 3 dimensional line segment. A straight line between two points in a 3d space. */
final class LineSeg3(val xStart: Double, val yStart: Double, val zStart: Double, val xEnd: Double, val yEnd: Double, val zEnd: Double) extends
  Dbl6Elem //with Product2[Pt3]
{ def typeStr: String = "Line3" 
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineSeg3]
  override def dbl1: Double = xStart
  override def dbl2 = yStart
  override def dbl3 = zStart
  override def dbl4 = xEnd
  override def dbl5 = yEnd
  override def dbl6 = zEnd
  def pStart: Pt3 = Pt3(xStart, yStart, zStart)
  def pEnd: Pt3 = Pt3(xEnd, yEnd, zEnd)
  //def func6Dou[T](f: (Double, Double, Double, Double, Double, Double) => T): T = f(x1, y1, z2, x2, y2, z2)
  def toXY: LineSeg = LineSeg(pStart.toXY, pEnd.toXY)
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