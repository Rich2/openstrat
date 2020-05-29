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
  implicit val slateImplicit: Slate[Ellipse] = (e, offset) => EllipseGen(0, 0, 0, 0, 0) // Circle.slate(offset)
}

/** An Ellipse whose axes are aligned to the x and y axes. The width axis is not necessarily the major axis. */
case class Ellipselign(xCen: Double, yCen: Double, rWidth: Double, rHeight: Double) extends Ellipse
{ override def fill(colour: Colour): GraphicElem = ???
  def fTrans(f: Vec2 => Vec2): GeomElem = ???
  def mirrorX: GeomElem = ???
  def mirrorXOffset(yOffset: Double): GeomElem = ???
  def mirrorY: GeomElem = ???
  def mirrorYOffset(xOffset: Double): GeomElem = ???
  def prolign(matrix: ProlignMatrix): GeomElem = ???
  def scale(operand: Double): GeomElem = ???
  def slate(xOffset: Double, yOffset: Double): GeomElem = ???
  def slate(offset: Vec2): GeomElem = ???
}