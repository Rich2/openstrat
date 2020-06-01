/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Ellipse extends Shape
{ def xCen: Double
  def yCen: Double
  final def cen: Vec2 = xCen vv yCen

 /* override def canEqual(that: Any): Boolean = that match
  { case e: Ellipse => true
    case _ => false
  }*/
}

object Ellipse {
  implicit def slateImplicit: Slate[Ellipse] = (e, offset) => EllipseGen(0, 0, 0, 0, 0) // Circle.slate(offset)
}

/** An Ellipse whose axes are aligned to the x and y axes. The width axis is not necessarily the major axis. */
case class Ellipselign(xCen: Double, yCen: Double, rWidth: Double, rHeight: Double) extends Ellipse
{ override def fill(colour: Colour): GraphicElem = ???
  def fTrans(f: Vec2 => Vec2): Transer = ???
  def mirrorX: Transer = ???
  def mirrorXOffset(yOffset: Double): Transer = ???
  def mirrorY: Transer = ???
  def mirrorYOffset(xOffset: Double): Transer = ???
  def prolign(matrix: ProlignMatrix): Transer = ???
  def scale(operand: Double): Transer = ???
  def slate(xOffset: Double, yOffset: Double): Transer = ???
  def slate(offset: Vec2): Transer = ???

  override def rotate90: Ellipselign = ???
}