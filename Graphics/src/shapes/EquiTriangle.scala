/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

final case class EquiTriangle(x0: Double, y0: Double, x2: Double, y2: Double) extends IsosTriangle
{
  override type ThisT = EquiTriangle
  override def height: Double = ???
  override def foreach[U](f: Vec2 => U): Unit = ???
  override def shapeAttribs: Arr[XANumeric] = ???
  override def fTrans(f: Vec2 => Vec2): ThisT = ???

  override def rotateRadians(radians: Double): Triangle = ???
  override def reflectX: ThisT = fTrans(_.reflectX)
  override def reflectY: ThisT = fTrans(_.reflectY)
  def reflectYOffset(xOffset: Double): ThisT = fTrans(_.reflectYOffset(xOffset))
  def reflectXOffset(yOffset: Double): ThisT = fTrans(_.reflectXOffset(yOffset))
  override def reflect(line: Line): TransElem = ???

  override def reflect(line: Sline): TransElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

  override def fillOld(fillColour: Colour): ShapeFill = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}