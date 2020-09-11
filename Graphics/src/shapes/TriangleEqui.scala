/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

final case class TriangleEqui(x0: Double, y0: Double, x2: Double, y2: Double) extends TriangleIsos
{
  override type ThisT = TriangleEqui
  override def height: Double = ???
  override def foreach[U](f: Vec2 => U): Unit = ???
  override def shapeAttribs: Arr[XANumeric] = ???
  override def fTrans(f: Vec2 => Vec2): ThisT = ???

  override def rotateRadians(radians: Double): Triangle = ???
  override def reflectX: ThisT = fTrans(_.reflectX)
  override def reflectY: ThisT = fTrans(_.reflectY)
  def reflectYOffset(xOffset: Double): ThisT = fTrans(_.reflectYOffset(xOffset))
  def reflectXOffset(yOffset: Double): ThisT = fTrans(_.reflectXOffset(yOffset))
  override def reflect(line: Line): Triangle = ???

  override def reflect(line: Sline): Triangle = ???

  override def scaleXY(xOperand: Double, yOperand: Double): Triangle = ???

  override def fillOld(fillColour: Colour): ShapeFillOld = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}