/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class EllipseGen(val xLeft: Double, val yLeft: Double, val xRight: Double, val yRight: Double, val rHeight: Double) extends Ellipse
{ //type AlignT = Ellipse
 // override def fTrans(f: Vec2 => Vec2): EllipseGen = ???
  def xCen: Double = (xLeft + xRight) /2
  def yCen: Double = (yLeft + yRight) / 2
  def vRight: Vec2 = xRight vv yRight
  //override def shear(xScale: Double, yScale: Double): EllipseGen = this
  //override def rotate(angle: Angle): EllipseGen = this
  //override def rotateRadians(radians: Double):  EllipseGen = this
  //override def slateOld(offset: Vec2): EllipseGen = EllipseGen(cen + offset, vRight + offset, upRadius)
  //override def scaleOld(operand: Double):  EllipseGen = EllipseGen(cen * operand * operand, vRight * operand, upRadius * operand)
  //override def mirror(line: Line2): EllipseGen = EllipseGen(cen.mirror(line), vRight.mirror(line), upRadius)
  def fTrans(f: Vec2 => Vec2): GeomElem = ???
  def mirrorX: GeomElem = ???
  def mirrorXOffset(yOffset: Double): GeomElem = ???
  def mirrorY: GeomElem = ???
  def mirrorYOffset(xOffset: Double): GeomElem = ???
  def prolign(matrix: ProlignMatrix): GeomElem = ???
  def scale(operand: Double): GeomElem = ???
  def slate(xOffset: Double, yOffset: Double): EllipseGen = EllipseGen(xLeft + xOffset, yLeft + yOffset, xRight + xOffset, yRight + yOffset, rHeight)
  def slate(offset: Vec2): GeomElem = ???

  override def fill(colour: Colour): GraphicElem = ???
}

object EllipseGen
{ def apply(vLeft: Vec2, vRight: Vec2, rHeight: Double): EllipseGen = new EllipseGen(vLeft.x, vLeft.y, vRight.x, vRight.y, rHeight)
}

