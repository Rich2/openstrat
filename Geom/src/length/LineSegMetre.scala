/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegMetre(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double) extends LineSegLike[PtMetre2]
{ def xStart: Metres = Metres(xStartMetres)
  def yStart: Metres = Metres(yStartMetres)
  def xEnd: Metres = Metres(xEndMetres)
  def yEnd: Metres = Metres(yEndMetres)
  def startPt: PtMetre2 = PtMetre2(xStart, yStart)
  def endPt: PtMetre2 = PtMetre2(xEnd, yEnd)
  def toLine2(f: PtMetre2 => Pt2): LineSeg = LineSeg(f(startPt), f(endPt))
}

object LineSegMetre
{
  def apply(startDist2: PtMetre2, endDist2: PtMetre2): LineSegMetre =
    new LineSegMetre(startDist2.xMetresNum, startDist2.yMetresNum, endDist2.xMetresNum, endDist2.yMetresNum)
}