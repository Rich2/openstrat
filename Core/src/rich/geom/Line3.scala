/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom

object Line3
{
   def apply(c1: Vec3, c2: Vec3): Line3 = new Line3(c1.x, c1.y, c1.z, c2.x, c2.y, c2.z)   
}

/** In geometry this is a line segment */
case class Line3(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double)
{
   def pt1: Vec3 = Vec3(x1, y1, z1)
   def pt2: Vec3 = Vec3(x2, y2, z2)
   def func6Dou[T](f: (Double, Double, Double, Double, Double, Double) => T): T = f(x1, y1, z2, x2, y2, z2)
   def toXY: Line2 = Line2(pt1.toXY, pt2.toXY)
   //def fTrans(f: Vec3 => Vec3): Line3 = Line2(f(pt1), f(pt2))
   
   //def toLatLongLine(f: Vec2 => LatLong): LatLongLine = LatLongLine(f(pt1), f(pt2))
}

case class LineDist3(x1: Dist, y1: Dist, z1: Dist, x2: Dist, y2: Dist, z2: Dist)
{
   def zsPos: Boolean = z1.pos && z2.pos
   def toXY: LineDist2 = LineDist2(x1, y1, x2, y2)
}

object LineDist3
{
   def apply(pt1: Dist3, pt2: Dist3): LineDist3 = new LineDist3(pt1.x, pt1.y, pt1.z, pt2.x, pt2.y, pt2.z)
}