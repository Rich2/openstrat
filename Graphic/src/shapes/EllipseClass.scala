/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class EllipseClass(val xLeft: Double, val yLeft: Double, val xRight: Double, val yRight: Double, val rHeight: Double) extends Ellipse
{ def xCen: Double = (xLeft + xRight) /2
  def yCen: Double = (yLeft + yRight) / 2
  def vLeft: Vec2 = xLeft vv yLeft
  def vRight: Vec2 = xRight vv yRight
  //override def shear(xScale: Double, yScale: Double): EllipseGen = this
  //override def rotate(angle: Angle): EllipseGen = this
  //override def rotateRadians(radians: Double):  EllipseGen = this
  //override def slateOld(offset: Vec2): EllipseGen = EllipseGen(cen + offset, vRight + offset, upRadius)
  //override def mirror(line: Line2): EllipseGen = EllipseGen(cen.mirror(line), vRight.mirror(line), upRadius)
 
  def mirrorX: TransElem = copy(xLeft, -yLeft, xRight, -yRight)
  def mirrorXOffset(yOffset: Double): TransElem = ???
  def mirrorY: TransElem = copy(-xLeft, yLeft, -xRight)
  def mirrorYOffset(xOffset: Double): TransElem = ???
  def prolign(matrix: ProlignMatrix): TransElem = ???
  def scale(operand: Double): EllipseClass = Ellipse(vLeft * operand, vRight * operand, rHeight * operand )
  def slate(xOffset: Double, yOffset: Double): EllipseClass = Ellipse(xLeft + xOffset, yLeft + yOffset, xRight + xOffset, yRight + yOffset, rHeight)
  def slate(offset: Vec2): TransElem = Ellipse(vLeft + offset, vRight + offset, rHeight)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate90: EllipseClass = ???

  override def rotate180: EllipseClass = ???

  override def rotate270: EllipseClass = ???

  override def rotateRadians(radians: Double): EllipseClass = ???

  override def fill(colour: Colour): DisplayElem = ???

  override def mirror(line: Line2): EllipseClass = ???
}

object EllipseClass
{ def apply(vLeft: Vec2, vRight: Vec2, rHeight: Double): EllipseClass = new EllipseClass(vLeft.x, vLeft.y, vRight.x, vRight.y, rHeight)
}

