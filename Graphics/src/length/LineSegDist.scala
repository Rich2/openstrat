/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2 dimensional line segment measured in metres, equivlent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegDist(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double)
{ def xStart: Metre = Metre(xStartMetres)
  def yStart: Metre = Metre(yStartMetres)
  def xEnd: Metre = Metre(xEndMetres)
  def yEnd: Metre = Metre(yEndMetres)
  def ptStart: Pt2M = Pt2M(xStart, yStart)
  def ptEnd: Pt2M = Pt2M(xEnd, yEnd)
  def toLine2(f: Pt2M => Pt2): LineSeg = LineSeg(f(ptStart), f(ptEnd))
}

object LineSegDist
{
  def apply(startDist2: Pt2M, endDist2: Pt2M): LineSegDist =
    new LineSegDist(startDist2.xMetres, startDist2.yMetres, endDist2.xMetres, endDist2.yMetres)
}