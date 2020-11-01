/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Sub traits include Line, LineDraw, Arc, ArcDraw, Bezier, BezierDraw */
trait CurveLikeOld
{ /** the x component of the start point often called x1 */
  def xStart: Double

  /** the y component of the start point often called y1 */
  def yStart: Double

  /** Start point often called p1 */
  final def pStart: Vec2 = xStart vv yStart

  /** the x component of the end point */
  def xEnd: Double
  /** the y component of the end point */
  def yEnd: Double
  /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
  final def pEnd: Vec2 = xEnd vv yEnd
}

trait CurveLikeOldGraphicElem extends CurveLikeOld with GraphicAffineElem