/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A 2 dimensional line segment measured in metres. A straight line between two points on a 2 dimensional flat surface. */
class LineDist2(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double)
{ def xStart: Dist = Dist(xStartMetres)
  def yStart: Dist = Dist(yStartMetres)
  def xEnd: Dist = Dist(xEndMetres)
  def yEnd: Dist = Dist(yEndMetres)
  def ptStart: Dist2 = Dist2(xStart, yStart)
  def ptEnd: Dist2 = Dist2(xEnd, yEnd)
  def toLine2(f: Dist2 => Vec2): Line2 = Line2(f(ptStart), f(ptEnd))
}
object LineDist2
{
  def apply(startDist2: Dist2, endDist2: Dist2): LineDist2 =
    new LineDist2(startDist2.xMetres, startDist2.yMetres, endDist2.xMetres, endDist2.yMetres)
}

case class LatLongLine(latStart: Double, longStart: Double, lat2: Double, long2: Double)
{
  def llStart = LatLong(latStart, longStart)
  def latLong2 = LatLong(lat2, long2)
}

object LatLongLine
{
  def apply(llStart: LatLong, ll2: LatLong): LatLongLine = new LatLongLine(llStart.lat, llStart.long, ll2.lat, ll2.long)
}
