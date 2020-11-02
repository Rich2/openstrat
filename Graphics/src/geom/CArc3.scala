/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Temporary name for new version of circular arc. */
case class CArc3(xStart: Double, yStart: Double, xCiMid: Double, yCiMid: Double, xEnd: Double, yEnd: Double) extends CurveSeg
{ /** The mid point (of the circumference) of the arc */
  def ciMid: Vec2 = xCiMid vv yCiMid

  /** The mid point of the chord of the arc. */
  def chMid: Vec2 = pStart mid pEnd

  def midMid: LineSeg = chMid.lineTo(ciMid)

  def midMidLen: Double = midMid.length

  /** The chord of this Arc */
  def chord: LineSeg = pStart.lineTo(pEnd)

  /** length of the chord of this arc. */
  def chordLen: Double = chord.length

  /** half of length of the chord of this arc. */
  def chordHLen: Double = chord.length

  def diameter: Double = chordHLen.squared / midMidLen + midMidLen

  @inline def radius: Double = diameter / 2
}
