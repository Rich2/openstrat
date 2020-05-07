/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Circular Arc */
final case class CArc(xStart: Double, yStart: Double, xCen: Double, yCen: Double, deltaRadians: Double) extends Transer
{ type AlignT = CArc
  def pCen: Vec2 = xCen vv yCen
  override def shear(xScale: Double, yScale: Double): CArc = ??? //EArc()
  def startAngleRadians: Double = (pStart - pCen).angleRadians
  def startAngleRadiansPos: Double = (pStart - pCen).angleRadiansPos
  def startAngle: Angle = (pStart - pCen).angle
  def endAngleRadians: Double = startAngleRadians + deltaRadians
  def endAngleRadiansPos: Double = startAngleRadiansPos + deltaRadians
  def endAngle: Angle = Angle(endAngleRadians)
  def pStart: Vec2 = xStart vv yStart
  def pEnd: Vec2 = pCen + endAngle.toVec2(radius)
  def midArcAngleRadians = startAngleRadians + deltaRadians / 2
  def midArcAngle = Angle(midArcAngleRadians)
  def pMidArc: Vec2 = pCen + midArcAngle.toVec2(radius)
  def radius: Double = (pStart - pCen).magnitude
  def clock: Boolean = deltaRadians < 0
  def antiClock: Boolean = deltaRadians >= 0
  def detltaDegs = deltaRadians.radiansToDegrees

  def pCtrl: Vec2 =
  { val sAng: Angle = startAngle
    val resultAngle = sAng.bisect(endAngle)
    val alphaAngle =  sAng.angleTo(endAngle) / 2
    pCen + resultAngle.toVec2(radius / alphaAngle.cos)
  }

  def xCtrl: Double = pCtrl.x
  def yCtrl: Double = pCtrl.y

  def xEnd: Double = pEnd.x
  def yEnd: Double = pEnd.y
  override def slateOld(offset: Vec2): CArc = CArc(pStart + offset, pCen + offset, deltaRadians)
  override def scaleOld(operand: Double): CArc = CArc(pStart * operand, pCen * operand, deltaRadians)

  override def rotateRadians(operandRadians: Double): CArc =
    CArc(pStart.rotateRadians(operandRadians), pCen.rotateRadians(operandRadians), deltaRadians)

  override def mirrorYOffset(xOffset: Double): CArc = CArc(pStart.mirrorYOffset(xOffset), pCen.mirrorYOffset(xOffset), -deltaRadians)
  override def mirrorXOffset(yOffset: Double): CArc = CArc(pStart.mirrorXOffset(yOffset), pCen.mirrorXOffset(yOffset), -deltaRadians)

  override def mirror(line: Line2): CArc = ???
  def draw(lineWidth: Double = 2.0, colour: Colour = Colour.Black) = CArcDraw(this,lineWidth, colour)

  override def fTrans(f: Vec2 => Vec2): CArc = ???
}

object CArc
{
  def apply(pStart: Vec2, pCen: Vec2, deltaRadians: Double): CArc = CArc(pStart.x, pStart.y, pCen.x, pCen.y, deltaRadians)
}