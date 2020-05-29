/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** In geometry this is a 3 dimensional line segment. A straight line between two points in a 3d space. */
case class Line3(xStart: Double, yStart: Double, zStart: Double, xEnd: Double, yEnd: Double, zEnd: Double) extends ProdDbl6
{ def typeStr: String = "Line3" 
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Line3]
  override def _1: Double = xStart
  override def _2 = yStart
  override def _3 = zStart
  override def _4 = xEnd
  override def _5 = yEnd
  override def _6 = zEnd
  def pStart: Vec3 = Vec3(xStart, yStart, zStart)
  def pEnd: Vec3 = Vec3(xEnd, yEnd, zEnd)
  //def func6Dou[T](f: (Double, Double, Double, Double, Double, Double) => T): T = f(x1, y1, z2, x2, y2, z2)
  def toXY: Line2 = Line2(pStart.toXY, pEnd.toXY)
   //def fTrans(f: Vec3 => Vec3): Line3 = Line2(f(pt1), f(pt2))
   
   //def toLatLongLine(f: Vec2 => LatLong): LatLongLine = LatLongLine(f(pt1), f(pt2))
}

object Line3
{
   def apply(pStart: Vec3, pEnd: Vec3): Line3 = new Line3(pStart.x, pStart.y, pStart.z, pEnd.x, pEnd.y, pEnd.z)   
}
