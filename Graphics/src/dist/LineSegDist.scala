/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2 dimensional line segment measured in metres, equivlent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegDist(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double)
{ def xStart: Metres = Metres(xStartMetres)
  def yStart: Metres = Metres(yStartMetres)
  def xEnd: Metres = Metres(xEndMetres)
  def yEnd: Metres = Metres(yEndMetres)
  def ptStart: Dist2 = Dist2(xStart, yStart)
  def ptEnd: Dist2 = Dist2(xEnd, yEnd)
  def toLine2(f: Dist2 => Pt2): LineSeg = LineSeg(f(ptStart), f(ptEnd))
}

object LineSegDist
{
  def apply(startDist2: Dist2, endDist2: Dist2): LineSegDist =
    new LineSegDist(startDist2.xMetres, startDist2.yMetres, endDist2.xMetres, endDist2.yMetres)
}