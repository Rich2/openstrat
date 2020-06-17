/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An Ellipse whose axes are aligned to the x and y axes. The width axis is not necessarily the major axis. */
case class Ellipselign(xCen: Double, yCen: Double, xRadius: Double, yRadius: Double) extends Ellipse
{ override type ThisT = Ellipselign
  override def fill(colour: Colour): ShapeFill = ???
  @inline def xMajor: Boolean = xRadius >= yRadius
  override def aRadius: Double = ife(xMajor, xRadius, yRadius)
  override def bRadius: Double = ife(xMajor, yRadius, xRadius)
  override def x1: Double = xCen + xRadius
  override def y1: Double = yCen
  override def x2: Double = xCen - xRadius
  override def y2: Double = yCen
  override def x3: Double = xCen
  override def y3: Double = yCen + yRadius

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  def fTrans(f: Vec2 => Vec2): Ellipselign = Ellipselign.cenV1V3(f(cen), f(v1), f(v3))
  
  override def rotateRadians(radians: Double): EllipseClass = ???

  override def mirror(line: Line2): EllipseClass = ???
}

object Ellipselign
{ def apply(cen: Vec2, xRadius: Double, yRadius: Double): Ellipselign = new Ellipselign(cen.x, cen.y, xRadius, yRadius)
  def cenV1V3(cen: Vec2, v1: Vec2, v3: Vec2): Ellipselign = new Ellipselign(cen.x, cen.y, (v1.x - cen.x).abs, (v3.y - cen.y).abs)
}