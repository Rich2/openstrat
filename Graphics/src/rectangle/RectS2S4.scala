/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle that is in part specified by points s2Cen and s4Cen. This is the [[Square.SquareImp]] and [[Rect.RectImp]] classes. This trait is purely
 *  for implementation. It does not have value as a library user interface type. */
trait RectS2S4 extends Rectangle
{
  final override def cen: Pt2 = sd3Cen midPt sd1Cen
  final override def xCen: Double = cen.x
  final override def yCen: Double = cen.y
  final override def width1: Double = sd4Cen.distTo(sd2Cen)
  final def rotationRadians: Double = alignAngle.radians
  @inline final override def alignAngle: AngleVec = sd4Cen.angleTo(sd2Cen).rotationFrom0
  final override def v1: Pt2 = sd2Cen + yVec2(width2 / 2).rotate(alignAngle)
  final override def x1: Double = v1.x
  final override def y1: Double = v1.y
  final override def v2: Pt2 = sd2Cen + yVec2(-width2 / 2).rotate(alignAngle)
  final override def x2: Double = v2.x
  final override def y2: Double = v2.y
  @inline final def v3: Pt2 = sd4Cen + yVec2(-width2 / 2).rotate(alignAngle)
  @inline final def x3: Double = v3.x
  @inline final def y3: Double = v3.y
  @inline final def v4: Pt2 = sd4Cen + yVec2(width2 / 2).rotate(alignAngle)
  @inline final def x4: Double = v4.x
  @inline final def y4: Double = v4.y

  final override def sd1Cen: Pt2 = v4 midPt v1
  final override def sd3Cen: Pt2 = v2 midPt v3

  final override  def sd2Cen: Pt2 = Pt2(xSd2Cen, ySd2Cen)
  def xS4Cen: Double
  def yS4Cen: Double
  final override def sd4Cen: Pt2 = Pt2(xS4Cen, yS4Cen)
}