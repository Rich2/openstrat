/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Graphic based on a [[CurveSeg]]. */
trait CurveSegGraphic extends GraphicElem
{
  def curveSeg: CurveSeg
  /** the x component of the start point often called x1 */
  def xStart: Double = curveSeg.xStart

  /** the y component of the start point often called y1 */
  def yStart: Double = curveSeg.yStart

  /** Start point often called p1 */
  final def pStart: Pt2 = curveSeg.pStart

  /** the x component of the end point. */
  def xEnd: Double = curveSeg.xEnd

  /** the y component of the end point. */
  def yEnd: Double = curveSeg.yEnd

  /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
  final def pEnd: Pt2 = curveSeg.pEnd
}

trait CurveSegDraw extends CurveSegGraphic
{
  def lineWidth: Double
  def colour: Colour
}