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
  def x2: Double
  def y2: Double
  def v2: Vec2 = x2 vv y2
  def aRadius: Double
  def bRadius: Double

  /* override def canEqual(that: Any): Boolean = that match
  { case e: Ellipse => true
    case _ => false
  }*/
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???
}

object Ellipse
{ /** The apply factory methods default to an EllipseClass. */
  def apply(xCen: Double, yCen: Double, x1: Double, y1: Double, radius3: Double): EllipseClass =
    new EllipseClass(xCen, yCen, x1, y1, radius3)

  /** The apply factory methods default to an EllipseClass. */
  def apply(cen: Vec2, v1: Vec2, radius3: Double): EllipseClass = new EllipseClass(cen.x, cen.y, v1.x, v1.y, radius3)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => Ellipse(ell.cen + offset, ell.v1 + offset, ell.bRadius)
}

/** An Ellipse whose axes are aligned to the x and y axes. The width axis is not necessarily the major axis. */
case class Ellipselign(xCen: Double, yCen: Double, xRadius: Double, yRadius: Double) extends Ellipse
{ override def fill(colour: Colour): ShapeFill = ???
  @inline def xMajor: Boolean = xRadius >= yRadius
  override def aRadius: Double = ife(xMajor, xRadius, yRadius)
  override def bRadius: Double = ife(xMajor, yRadius, xRadius)
  override def x1: Double = ???

  override def y1: Double = ???

  override def x2: Double = ???

  override def y2: Double = ???
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