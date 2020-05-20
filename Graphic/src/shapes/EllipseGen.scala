/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class EllipseGen(val xLeft: Double, val yLeft: Double, val xRight: Double, val yRight: Double, val radiusB: Double) extends Ellipse
{ //type AlignT = Ellipse
 // override def fTrans(f: Vec2 => Vec2): EllipseGen = ???
  def xCen: Double = (xLeft + xRight) /2
  def yCen: Double = (yLeft + yRight) / 2
  def cen: Vec2 = xCen vv yCen
  def vRight: Vec2 = xRight vv yRight
  //override def shear(xScale: Double, yScale: Double): EllipseGen = this
  //override def rotate(angle: Angle): EllipseGen = this
  //override def rotateRadians(radians: Double):  EllipseGen = this
  //override def slateOld(offset: Vec2): EllipseGen = EllipseGen(cen + offset, vRight + offset, upRadius)
  //override def scaleOld(operand: Double):  EllipseGen = EllipseGen(cen * operand * operand, vRight * operand, upRadius * operand)
  //override def mirror(line: Line2): EllipseGen = EllipseGen(cen.mirror(line), vRight.mirror(line), upRadius)
  def fTrans(f: Vec2 => Vec2): GeomElemNew = ???
  def mirrorX: GeomElemNew = ???
  def mirrorXOffset(yOffset: Double): GeomElemNew = ???
  def mirrorY: GeomElemNew = ???
  def mirrorYOffset(xOffset: Double): GeomElemNew = ???
  def prolign(matrix: ProlignMatrix): GeomElemNew = ???
  def scale(operand: Double): GeomElemNew = ???
  def slate(xOffset: Double, yOffset: Double): GeomElemNew = ???
  def slate(offset: Vec2): GeomElemNew = ???

  override def fill(colour: Colour): GraphicElem = ???
}

object EllipseGen
{ def apply(vCen: Vec2, vRight: Vec2, upRadius: Double): EllipseGen = new EllipseGen(vCen.x, vCen.y, vRight.x, vRight.y, upRadius)
}

