/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

final case class TriangleEqui(x0: Double, y0: Double, x2: Double, y2: Double) extends TriangleIsos
{
  type ThisT = TriangleEqui
  override def height: Double = ???
  override def foreach[U](f: Vec2 => U): Unit = ???
  override def attribs: Arr[XANumeric] = ???
  override def fTrans(f: Vec2 => Vec2): ThisT = ???

  override def rotateRadians(radians: Double): Triangle = ???
  override def reflectX: ThisT = fTrans(_.reflectX)
  override def reflectY: ThisT = fTrans(_.reflectY)
  override def reflectYOffset(xOffset: Double): ThisT = fTrans(_.reflectYOffset(xOffset))
  override def reflectXOffset(yOffset: Double): ThisT = fTrans(_.reflectXOffset(yOffset))
  override def reflect(line: Line): Triangle = ???

  override def reflect(line: LineSeg): Triangle = ???

  override def xyScale(xOperand: Double, yOperand: Double): Triangle = ???

  override def fillOld(fillColour: Colour): ShapeFill = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}