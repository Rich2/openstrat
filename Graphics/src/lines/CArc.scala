/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

/** Circular arc. */
case class CArc(xStart: Double, yStart: Double, xApex: Double, yApex: Double, xEnd: Double, yEnd: Double) extends CurveSeg with AffinePreserve
{ override type ThisT = CArc
  override def fTrans(f: Pt2 => Pt2): ThisT = CArc(f(pStart), f(apex), f(pEnd))

  /** The mid or half way point (of the circumference) of the arc */
  def apex: Pt2 = xApex pp yApex

  /** The mid point of the chord of the arc. */
  def chordCen: Pt2 = pStart mid pEnd

  /** Line segment that bisects the segment of this arc. */
  def median: LineSeg = chordCen.lineTo(apex)

  /** The height of the arc. The length from the bisection of the chord to the apex. */
  def height: Double = median.length

  /** The chord of this Arc */
  def chord: LineSeg = pStart.lineTo(pEnd)

  /** length of the chord of this arc. */
  def width: Double = chord.length

  /** half of length of the chord of this arc. */
  def hWidth: Double = chord.length / 2

  def diameter: Double = hWidth.squared / height + height

  @inline def radius: Double = diameter / 2

  def cen: Pt2 = apex + apex.vecTo(chordCen) * radius / height

  def startAngle: Angle = cen.angleTo(pStart)
  def endAngle: Angle = cen.angleTo(pEnd)

  def draw(colour: Colour = Black, lineWidth: Double = 2): CArcDraw3 = CArcDraw3(this, colour, lineWidth)
}

object CArc
{
  def apply(pStart: Pt2, apex: Pt2, pEnd: Pt2): CArc = new CArc(pStart.x, pStart.y, apex.x, apex.y, pEnd.x, pEnd.y)
}

case class CArcDraw3(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends CurveSegDraw with AffinePreserve with CanvElem
{
  override type ThisT = CArcDraw3

  override def fTrans(f: Pt2 => Pt2): CArcDraw3 = CArcDraw3(curveSeg.fTrans(f), colour, lineWidth)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.cArcDraw3(this)
}