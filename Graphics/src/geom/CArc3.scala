/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Temporary name for new version of circular arc. */
case class CArc3(xStart: Double, yStart: Double, xCiMid: Double, yCiMid: Double, xEnd: Double, yEnd: Double) extends CurveSeg
{ /** The mid or half way point (of the circumference) of the arc */
  def apex: Vec2 = xCiMid vv yCiMid

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
