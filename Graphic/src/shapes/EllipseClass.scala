/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class EllipseClass(val x1: Double, val y1: Double, val x2: Double, val y2: Double, val rHeight: Double) extends Ellipse
{ def xCen: Double = (x1 + x2) /2
  def yCen: Double = (y1 + y2) / 2
  def vLeft: Vec2 = x1 vv y1
  def vRight: Vec2 = x2 vv y2
  //override def shear(xScale: Double, yScale: Double): EllipseGen = this
  //override def rotate(angle: Angle): EllipseGen = this
  //override def rotateRadians(radians: Double):  EllipseGen = this
  //override def slateOld(offset: Vec2): EllipseGen = EllipseGen(cen + offset, vRight + offset, upRadius)
  //override def mirror(line: Line2): EllipseGen = EllipseGen(cen.mirror(line), vRight.mirror(line), upRadius)
 
  def mirrorX: TransElem = copy(x1, -y1, x2, -y2)
  def mirrorXOffset(yOffset: Double): TransElem = ???
  def mirrorY: TransElem = copy(-x1, y1, -x2)
  def mirrorYOffset(xOffset: Double): TransElem = ???
  def prolign(matrix: ProlignMatrix): TransElem = ???
  def scale(operand: Double): EllipseClass = Ellipse(vLeft * operand, vRight * operand, rHeight * operand )
  def slate(xOffset: Double, yOffset: Double): EllipseClass = Ellipse(x1 + xOffset, y1 + yOffset, x2 + xOffset, y2 + yOffset, rHeight)
  def slate(offset: Vec2): TransElem = Ellipse(vLeft + offset, vRight + offset, rHeight)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate90: EllipseClass = ???

  override def rotate180: EllipseClass = ???

  override def rotate270: EllipseClass = ???

  override def rotateRadians(radians: Double): EllipseClass = ???

  override def fill(colour: Colour): ShapeFill = ???

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def mirror(line: Line2): EllipseClass = ???
}

object EllipseClass
{ def apply(vLeft: Vec2, vRight: Vec2, rHeight: Double): EllipseClass = new EllipseClass(vLeft.x, vLeft.y, vRight.x, vRight.y, rHeight)
}

