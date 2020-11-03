/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

/** Temporary name for new version of circular arc. */
case class CArc3(xStart: Double, yStart: Double, xApex: Double, yApex: Double, xEnd: Double, yEnd: Double) extends CurveSeg with AffinePreserve
{ override type ThisT = CArc3
  override def fTrans(f: Vec2 => Vec2): ThisT = CArc3(f(pStart), f(apex), f(pEnd))

  /** The mid or half way point (of the circumference) of the arc */
  def apex: Vec2 = xApex vv yApex

  /** The mid point of the chord of the arc. */
  def chordCen: Vec2 = pStart mid pEnd

  /** Line segment that bisects the segment of this arc. */
  def median: LineSeg = chordCen.lineTo(apex)

  /** The height of the arc. The length from the bisection of the chord to the apex. */
  def height: Double = median.length

  /** The chord of this Arc */
  def chord: LineSeg = pStart.lineTo(pEnd)

  /** length of the chord of this arc. */
  def width: Double = chord.length

  /** half of length of the chord of this arc. */
  def hWidth: Double = chord.length

  def diameter: Double = hWidth.squared / height + height

  @inline def radius: Double = diameter / 2


}

object CArc3
{
  def apply(pStart: Vec2, apex: Vec2, pEnd: Vec2): CArc3 = new CArc3(pStart.x, pStart.y, apex.x, apex.y, pEnd.x, pEnd.y)
}

case class CArcDraw3(curveSeg: CArc3, colour: Colour = Black, lineWidth: Double = 2) extends CurveSegDraw with AffinePreserve
{
  override type ThisT = CArcDraw3

  override def fTrans(f: Vec2 => Vec2): CArcDraw3 = CArcDraw3(curveSeg.fTrans(f), colour, lineWidth)
}