/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
final case class RectlignImplement(width: Double, height: Double, xCen: Double, yCen: Double) extends Rectlign
{ override def fTrans(f: Vec2 => Vec2): RectlignImplement = ???
  override def shapeAttribs: Arr[XANumeric] = ???
  override def rotateRadians(radians: Double): Rect = ???
  override def reflectX: RectlignImplement = fTrans(_.reflectX)
  override def reflectY: RectlignImplement = fTrans(_.reflectY)
  override def reflectXOffset(yOffset: Double): RectlignImplement = fTrans(_.reflectXOffset(yOffset))
  override def reflectYOffset(xOffset: Double): RectlignImplement = fTrans(_.reflectYOffset(xOffset))
  //override def reflect(line: Line): Polygon = ???
  //override def reflect(line: Sline): Polygon = ???

  override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

  override def fillOld(fillColour: Colour): ShapeFill = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

/** Companion object for the Rectlign class */
object RectlignImplement
{ def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): RectlignImplement = new RectlignImplement(width, height, cen.x, cen.y)
}