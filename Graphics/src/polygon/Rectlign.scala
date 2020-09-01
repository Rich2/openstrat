/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
final case class Rectlign(width: Double, height: Double, xCen: Double, yCen: Double) extends Rectanglelign
{ override type ThisT = Rectlign
  override def fTrans(f: Vec2 => Vec2): Rectlign = ???
  override def shapeAttribs: Arr[XANumeric] = ???
  override def rotateRadians(radians: Double): Rect = ???
  override def reflectX: ThisT = fTrans(_.reflectX)
  override def reflectY: ThisT = fTrans(_.reflectY)
  def reflectXOffset(yOffset: Double): ThisT = fTrans(_.reflectXOffset(yOffset))
  def reflectYOffset(xOffset: Double): ThisT = fTrans(_.reflectYOffset(xOffset))
  override def reflect(line: Line): TransElem = ???
  override def reflect(line: Sline): TransElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

  override def fillOld(fillColour: Colour): ShapeFill = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

/** Companion object for the Rectlign class */
object Rectlign
{ def apply(cen: Vec2, width: Double, height: Double): Rectlign = new Rectlign(width, height, cen.x, cen.y)
}