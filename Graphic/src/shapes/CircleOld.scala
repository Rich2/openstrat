/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Slated for removal. */
final case class CircleOld(radius: Double, x: Double, y: Double) extends AffinePreserve
{ override type ThisT = CircleOld
  override def fTrans(f: Vec2 => Vec2): CircleOld = CircleOld(radius, f(vCen))
  def vCen: Vec2 = x vv y
 // override def shear(xScale: Double, yScale: Double): CircleOld = ??? // new EllipseGen(x, y, x + radius, 0, radius)
  override def rotateRadians(radians: Double): CircleOld = CircleOld(radius, vCen.rotateRadians(radians))
  override def slate(offset: Vec2): CircleOld = CircleOld(radius, x + offset.x, y + offset.y)
  override def scale(operand: Double): CircleOld = CircleOld(radius * operand, x * operand, y * operand)

  override def reflect(line: Line): CircleOld = CircleOld(radius, vCen.mirror(line))

  def fill(colour: Colour): CircleFill = ??? // CircleFillOld(this, colour)
  def draw(lineWidth: Double = 2, colour: Colour): CircleDraw = ??? // CircleDrawOld(this, lineWidth, colour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 2, lineColour: Colour): CircleFillDraw = ??? 
  //CircleFillDrawOld(this, fillColour, lineWidth, lineColour)
}

/** This object provides factory methods for circles. */
object CircleOld
{ def apply(radius: Double, cen: Vec2 = Vec2Z): CircleOld = new CircleOld(radius, cen.x, cen.y)
}