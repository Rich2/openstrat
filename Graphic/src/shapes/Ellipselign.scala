/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An Ellipse whose axes are aligned to the x and y axes. The width axis is not necessarily the major axis. */
case class Ellipselign(xCen: Double, yCen: Double, xRadius: Double, yRadius: Double) extends Ellipse
{ override def fill(colour: Colour): ShapeFill = ???
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
  //def fTrans(f: Vec2 => Vec2): TransElem = ???
  def mirrorX: Ellipselign = ???
  def mirrorXOffset(yOffset: Double): TransElem = ???
  def mirrorY: TransElem = ???
  def mirrorYOffset(xOffset: Double): TransElem = ???
  def prolign(matrix: ProlignMatrix): TransElem = ???
  def scale(operand: Double): TransElem = ???
  def slate(xOffset: Double, yOffset: Double): Ellipselign = ???
  def slate(offset: Vec2): Ellipselign = ???

  override def rotate90: Ellipselign = ???

  override def rotate180: Ellipselign = ???

  override def rotate270: Ellipselign = ???

  override def rotateRadians(radians: Double): EllipseClass = ???

  override def mirror(line: Line2): EllipseClass = ???
}