/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LineSegLength2[VT <: PtLength2] extends LineSegLike[VT]
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT
  def xStartMetresNum: Double
  def yStartMetresNum: Double
}

object LineSegLength2
{
  implicit class LineSegLength2Extensions[VT <: PtLength2](val thisSeg: LineSegLength2[VT])
  {
    def /(operand: LengthMetric): LineSeg = LineSeg(thisSeg.startPt / operand, thisSeg.endPt / operand)
  }
}