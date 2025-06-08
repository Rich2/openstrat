/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait CurveTail
{ /** The end point of the [[CurveSeg]]. */
  def endPt: Pt2
  
  /** The full [[CurveSeg]] */
  def curveSeg(startX: Double, startY: Double): CurveSeg

  /** The full [[CurveSeg]] */
  def curveSeg(startPt: Pt2): CurveSeg
}

case class CArcTail(cenX: Double, cenY: Double, endX: Double, endY: Double) extends CurveTail
{ override def endPt: Pt2 = Pt2(endX, endY)
  override def curveSeg(startX: Double, startY: Double): CurveSeg = CArc(startX, startY, cenX, cenY, endX, endY, 0)
  override def curveSeg(startPt: Pt2): CurveSeg = CArc(startPt.x, startPt.y, cenX, cenY, endX, endY, 0)
}