/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

case class LineDist2(x1: Dist, y1: Dist, x2: Dist, y2: Dist)
{
   def pt1: Dist2 = Dist2(x1, y1)
   def pt2: Dist2 = Dist2(x2, y2)
   def toLine2(f: Dist2 => Vec2): Line2 = Line2(f(pt1), f(pt2))
}

case class LatLongLine(lat1: Double, long1: Double, lat2: Double, long2: Double)
{
   def latLong1 = LatLong(lat1, long1)
   def latLong2 = LatLong(lat2, long2)
}

object LatLongLine
{
   def apply(ll1: LatLong, ll2: LatLong): LatLongLine = new LatLongLine(ll1.lat, ll1.long, ll2.lat, ll2.long)
}
