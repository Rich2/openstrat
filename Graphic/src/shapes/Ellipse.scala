/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Ellipse extends Shape
{ def xCen: Double
  def yCen: Double
  final def cen: Vec2 = xCen vv yCen
  def x1: Double
  def y1: Double
  final def v1: Vec2 = x1 vv y1

  /* override def canEqual(that: Any): Boolean = that match
  { case e: Ellipse => true
    case _ => false
  }*/
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???
}

object Ellipse
{ /** The apply factory methods default to an EllipseClass. */
  def apply(xLeft: Double, yLeft: Double, xRight: Double, yRight: Double, rHeight: Double): EllipseClass =
    new EllipseClass(xLeft, yLeft, xRight, yRight, rHeight)

  /** The apply factory methods default to an EllipseClass. */
  def apply(vLeft: Vec2, vRight: Vec2, rHeight: Double): EllipseClass = new EllipseClass(vLeft.x, vLeft.y, vRight.x, vRight.y, rHeight)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => EllipseClass(0, 0, 0, 0, 0) // Circle.slate(offset)
}

/** An Ellipse whose axes are aligned to the x and y axes. The width axis is not necessarily the major axis. */
case class Ellipselign(xCen: Double, yCen: Double, rWidth: Double, rHeight: Double) extends Ellipse
{ override def fill(colour: Colour): ShapeFill = ???

  override def x1: Double = ???

  override def y1: Double = ???
  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  def fTrans(f: Vec2 => Vec2): TransElem = ???
  def mirrorX: TransElem = ???
  def mirrorXOffset(yOffset: Double): TransElem = ???
  def mirrorY: TransElem = ???
  def mirrorYOffset(xOffset: Double): TransElem = ???
  def prolign(matrix: ProlignMatrix): TransElem = ???
  def scale(operand: Double): TransElem = ???
  def slate(xOffset: Double, yOffset: Double): TransElem = ???
  def slate(offset: Vec2): TransElem = ???

  override def rotate90: Ellipselign = ???

  override def rotate180: Ellipselign = ???

  override def rotate270: Ellipselign = ???

  override def rotateRadians(radians: Double): EllipseClass = ???

  override def mirror(line: Line2): EllipseClass = ???
}