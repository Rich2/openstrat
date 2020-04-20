/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

final case class ArcNew(xCen: Double, yCen: Double, r: Double, startRadians: Double, endRadians: Double ) extends TransSimer
{ type ThisT = ArcNew
  def pCen: Vec2 = xCen vv yCen
  def startAngle: Angle = Angle(startRadians)
  def endAngle: Angle = Angle(endRadians)
  def pStart: Vec2 = pCen + startAngle.toVec2(r)
  def pEnd: Vec2 = pCen + endAngle.toVec2(r)
  def xStart: Double = pStart.x
  def yStart: Double = pStart.y
  def xEnd: Double = pEnd.x
  def yEnd: Double = pEnd.y
  override def slate(offset: Vec2): ArcNew = ArcNew(xCen + offset.x, yCen + offset.y, r, startRadians, endRadians)

  override def scale(operand: Double): ArcNew = ArcNew(xCen * operand, yCen * operand, r * operand, startRadians, endRadians)
}
