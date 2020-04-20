/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Circular Arc */
final case class CArc(xCen: Double, yCen: Double, xStart: Double, yStart: Double, deltaRadians: Double ) extends TransSimer
{ type ThisT = CArc
  def pCen: Vec2 = xCen vv yCen
  //def startAngle: Angle = Angle(startRadians)
  //def endAngle: Angle = Angle(endRadians)
  def pStart: Vec2 = xStart vv yStart
  def pEnd: Vec2 = ??? //pCen + endAngle.toVec2(r)

  def xEnd: Double = pEnd.x
  def yEnd: Double = pEnd.y
  override def slate(offset: Vec2): CArc = CArc(pCen + offset, pStart + offset, deltaRadians)
  override def scale(operand: Double): CArc = CArc(xCen * operand, yCen * operand, xStart * operand, yStart * operand, deltaRadians)

  override def rotateRadians(operandRadians: Double): CArc =
    CArc(pCen.rotateRadians(operandRadians), pStart.rotateRadians(operandRadians), deltaRadians)
}

object CArc
{
  def apply(pCen: Vec2, pStart: Vec2, deltaRadians: Double): CArc = CArc(pCen.x, pCen.y, pStart.x, pStart.y, deltaRadians)
}
