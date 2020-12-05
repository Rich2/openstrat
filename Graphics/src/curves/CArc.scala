/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

/** Circular arc. */
class CArc private(val xStart: Double, val yStart: Double, val xApex: Double, val yApex: Double, val xEnd: Double, val yEnd: Double) extends EArc with
  SimilarPreserve
{ override type ThisT = CArc
  override def fTrans(f: Pt2 => Pt2): ThisT = CArc(f(pStart), f(apex), f(pEnd))

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???

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

 // def startAngle: Angle = cen.angleTo(pStart)
//  def endAngle: Angle = cen.angleTo(pEnd)

  //** this normalises atan2 to 0-360 Degrees
  def atan2Deg(y:Double, x:Double):Double = 
  { var theta = math.atan2(-y, -x)
    if (theta.isNaN) theta = Pi1 // NOT sure about this when y & x = 0 
    theta = (-theta*180/Pi1 + 180) % 360
    //theta = (Angle(theta).secs.toInt/SecsInDeg).toDouble
    //debvar(theta)
    theta
  }
  def startAngle:Double = atan2Deg(yStart-cen.y, xStart-cen.x)
  def endAngle:Double = atan2Deg(yEnd-cen.y, xEnd-cen.x)
  // def startAngle: Double = atan2Deg(yStart-cen.y, xStart-cen.x)
  // def endAngle: Double = atan2Deg(yEnd-cen.y, xEnd-cen.x)
  // def startAngle: Double = Angle(atan2Deg(yStart-cen.y, xStart-cen.x)).degs
  // def endAngle: Double = Angle(atan2Deg(yEnd-cen.y, xEnd-cen.x)).degs
  // def startAngle:Angle = cen.angleTo(pStart) 
  // def endAngle:Angle = cen.angleTo(pEnd)

  def angle:Double = endAngle - startAngle
  // def startAngle: Double = {var sa = fixAtan(yStart-cen.y, xStart-cen.x); if ((sa < 0) || (sa == NaN)) sa = 0; sa}
  // def endAngle: Double = fixAtan(yEnd-cen.y, xEnd-cen.x)


  def draw(colour: Colour = Black, lineWidth: Double = 2): CArcDraw = CArcDraw(this, colour, lineWidth)
}

object CArc
{
  def apply(pStart: Pt2, apex: Pt2, pEnd: Pt2): CArc = new CArc(pStart.x, pStart.y, apex.x, apex.y, pEnd.x, pEnd.y)
  def xys(xStart: Double, yStart: Double, xApex: Double, yApex: Double, xEnd: Double, yEnd: Double): CArc =
    new CArc(xStart, yStart, xApex, yApex, xEnd, yEnd)

  def centre(pStart: Pt2, centre: Pt2, pEnd: Pt2, isShort: Boolean = true): CArc =
  {
    val startAng: Angle = pStart.angleFrom(centre)
    val endAng: Angle = pEnd.angleFrom(centre)
    val angDelta: AngleVec = ??? // endAng - startAng
    ???
  }
}
