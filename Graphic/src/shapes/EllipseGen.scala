/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait EllipseLike extends Transer with Shape//Affer
{ type AlignT = EllipseLike
  //override def shear(xScale: Double, yScale: Double): Ellipse
}

class EllipseGen(val xCen: Double, val yCen: Double, val xRight: Double, val yRight: Double, val upRadius: Double) extends EllipseLike with Transer//ffer
{ //type AlignT = Ellipse
  override def fTrans(f: Vec2 => Vec2): EllipseLike = ???
  def vCen: Vec2 = xCen vv yCen
  def vRight: Vec2 = xRight vv yRight
  override def shear(xScale: Double, yScale: Double): EllipseGen = this
  override def rotate(angle: Angle): EllipseGen = this
  override def rotateRadians(radians: Double):  EllipseGen = this
  override def slateOld(offset: Vec2): EllipseGen = EllipseGen(vCen + offset, vRight + offset, upRadius)
  override def scaleOld(operand: Double):  EllipseGen = EllipseGen(vCen * operand * operand, vRight * operand, upRadius * operand)
  override def mirror(line: Line2): EllipseGen = EllipseGen(vCen.mirror(line), vRight.mirror(line), upRadius)

  override def fill(colour: Colour): GraphicElem = ???
}

object EllipseGen
{ def apply(vCen: Vec2, vRight: Vec2, upRadius: Double): EllipseGen = new EllipseGen(vCen.x, vCen.y, vRight.x, vRight.y, upRadius)
}