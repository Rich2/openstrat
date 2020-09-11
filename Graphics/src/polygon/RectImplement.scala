/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A rectangle class that has position and may not be aligned to the X and Y axes. */
final class RectImplement(val x0: Double, val y0: Double, val x1: Double, val y1: Double, val width: Double) extends RectV0V1
{ override type ThisT = RectImplement
  override def height: Double = (v1 - v2).magnitude
  override def fTrans(f: Vec2 => Vec2): RectImplement = RectImplement.points(f(cen), f(v0), f(v1))

  override def productArity: Int = 5

  override def productElement(n: Int): Any = ???

  override def rotation: Angle = (v0 - v3).angle

  override def rotateRadians(radians: Double): RectImplement = ???
  override def reflectX: RectImplement = RectImplement.v0v1(v1.reflectX, v0.reflectX, width)
  override def reflectY: RectImplement = RectImplement.v0v1(v1.reflectY, v0.reflectY, width)
  def reflectXOffset(yOffset: Double): RectImplement = RectImplement.v0v1(v1.reflectXOffset(yOffset), v0.reflectXOffset(yOffset), width)
  def reflectYOffset(xOffset: Double): RectImplement = RectImplement.v0v1(v1.reflectYOffset(xOffset), v0.reflectYOffset(xOffset), width)

  override def reflect(line: Line): RectImplement = RectImplement.v0v1(v1.reflect(line), v0.reflect(line), width)

  override def reflect(line: Sline): RectImplement = ???

  override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

  override def fillOld(fillColour: Colour): ShapeFillOld = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

object RectImplement
{
  /** The standard factory method for producing a Rect from width, height, position and rotation. position and rotation take default values */
  def apply(width: Double, height: Double, posn: Vec2, rotation: Angle = 0.degs): RectImplement = new RectImplement(posn.x, 0, 0, 0, width)

  /** The standard factory method for producing a Rect from width, height, the x position, the y position  and the rotation. Rotation has a default
   *  value of 0 degrees. If you want the default position of a rectangle centred at 0, 0, then use the apply method. */
  def xy(width: Double, height: Double, xCen: Double, yCen: Double, rotation: Angle = 0.degs): RectImplement = new RectImplement(xCen, 0, 0, 0, 0)

  /** Factory method for creating a [[RectImplement]] rectangle from the points v0, v1, and the width. */
  def v0v1(v0: Vec2, v1: Vec2, width: Double): RectImplement = new RectImplement(v0.x, v0.y, v1.x, v1.y, width)
  def points(cen: Vec2, topRight: Vec2, bottomRight: Vec2): RectImplement = ??? // new Rect(cen.x, cen.y, topRight.x, topRight.y, bottomRight.x, bottomRight.y)
}