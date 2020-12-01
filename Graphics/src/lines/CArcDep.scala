/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Circular Arc to be removed along with CArcOld. */
final case class CArcDep(xStart: Double, yStart: Double, xCen: Double, yCen: Double, deltaRadians: Double) extends SimilarPreserve
{ type ThisT = CArcDep
  override def fTrans(f: Pt2 => Pt2): CArcDep = ???
  def cen: Pt2 = xCen pp yCen

  def startAngleRadians: Double = pStart.angleFrom(cen).radians
  def startAngleRadiansPos: Double = pStart.vecFrom(cen).angleRadiansPos
  def startAngle: Angle = pStart.angleFrom(cen)
  def endAngleRadians: Double = startAngleRadians + deltaRadians
  def endAngleRadiansPos: Double = startAngleRadiansPos + deltaRadians
  def endAngle: Angle = Angle.radians(endAngleRadians)
  def pStart: Pt2 = xStart pp yStart
  def pEnd: Pt2 = cen + endAngle.toVec2(radius)
  def midArcAngleRadians = startAngleRadians + deltaRadians / 2
  def midArcAngle = Angle.radians(midArcAngleRadians)
  def pMidArc: Pt2 = cen + midArcAngle.toVec2(radius)
  def radius: Double = pStart.distTo(cen)
  def clock: Boolean = deltaRadians < 0
  def antiClock: Boolean = deltaRadians >= 0
  def detltaDegs = deltaRadians.radiansToDegs

  def pCtrl: Pt2 =
  { val sAng: Angle = startAngle
    val resultAngle = sAng.bisect(endAngle)
    val alphaAngle =  sAng.angleTo(endAngle) / 2
    cen + resultAngle.toVec2(radius / alphaAngle.cos)
  }

  def xCtrl: Double = pCtrl.x
  def yCtrl: Double = pCtrl.y

  def xEnd: Double = pEnd.x
  def yEnd: Double = pEnd.y
  //override def slate(offset: Vec2Like): CArc = CArc(pStart + offset, cen + offset, deltaRadians)
  override def scale(operand: Double): CArcDep = CArcDep(pStart * operand, cen * operand, deltaRadians)

  override def rotate(angle: Angle): CArcDep = CArcDep(pStart.rotate(angle), cen.rotate(angle), deltaRadians)

  override def reflect(lineLike: LineLike): CArcDep = ???
  def draw(lineWidth: Double = 2.0, colour: Colour = Colour.Black) = CArcDraw(this,lineWidth, colour)

  override def xyScale(xOperand: Double, yOperand: Double): GeomElem = ???

  override def xShear(operand: Double): GeomElem = ???

  override def yShear(operand: Double): GeomElem = ???
}

object CArcDep
{
  def apply(pStart: Pt2, pCen: Pt2, deltaRadians: Double): CArcDep = CArcDep(pStart.x, pStart.y, pCen.x, pCen.y, deltaRadians)
}