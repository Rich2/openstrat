/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegMetre(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double) extends LineSegLike[PtM2]
{ def xStart: Length = Length(xStartMetres)
  def yStart: Length = Length(yStartMetres)
  def xEnd: Length = Length(xEndMetres)
  def yEnd: Length = Length(yEndMetres)
  def startPt: PtM2 = PtM2(xStart, yStart)
  def endPt: PtM2 = PtM2(xEnd, yEnd)
  def toLine2(f: PtM2 => Pt2): LineSeg = LineSeg(f(startPt), f(endPt))
}

object LineSegMetre
{
  def apply(startDist2: PtM2, endDist2: PtM2): LineSegMetre =
    new LineSegMetre(startDist2.xMetresNum, startDist2.yMetresNum, endDist2.xMetresNum, endDist2.yMetresNum)
}