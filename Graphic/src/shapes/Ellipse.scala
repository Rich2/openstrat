/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Ellipse extends Shape//Affer
{ def xCen: Double
  def yCen: Double
  def cen: Vec2
}

case class Ellipselign(xCen: Double, yCen: Double, radiusA: Double, radiusB: Double) extends Ellipse
{
  override def cen: Vec2 = xCen vv yCen
  override def fill(colour: Colour): GraphicElem = ???
  def fTrans(f: Vec2 => Vec2): GeomElemNew = ???
  def mirrorX: GeomElemNew = ???
  def mirrorXOffset(yOffset: Double): GeomElemNew = ???
  def mirrorY: GeomElemNew = ???
  def mirrorYOffset(xOffset: Double): GeomElemNew = ???
  def prolign(matrix: ProlignMatrix): GeomElemNew = ???
  def scale(operand: Double): GeomElemNew = ???
  def slate(xOffset: Double, yOffset: Double): GeomElemNew = ???
  def slate(offset: Vec2): GeomElemNew = ???
}

