/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class EllipseClass(xCen: Double, yCen: Double, x1: Double, y1: Double, x3: Double, y3: Double) extends Ellipse with TransAffElem
{  override type ThisT = EllipseClass
  def x2: Double = 2 * xCen - x1
  def y2: Double = 2 * yCen - y1
  def majorRadius: Double = (v1 - cen).magnitude
  def minorRadius: Double = (v3 - cen).magnitude
  override def fTrans(f: Vec2 => Vec2): EllipseClass = EllipseClass(f(cen), f(v1), f(v3))
  override def fill(colour: Colour): EllipseFill = EllipseFill(this, colour)
  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

object EllipseClass
{ def apply(vLeft: Vec2, vRight: Vec2, vUp: Vec2): EllipseClass = new EllipseClass(vLeft.x, vLeft.y, vRight.x, vRight.y, vUp.x, vUp.y)
  def cenV1V3(cen: Vec2, v1: Vec2, v3: Vec2): EllipseClass = new EllipseClass(cen.x, cen.y, v1.x, v1.y, v3.x, v3.y)
}

