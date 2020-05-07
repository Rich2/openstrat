/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait EllipseLike extends Transer//Affer
{ type AlignT = EllipseLike
  //override def shear(xScale: Double, yScale: Double): Ellipse
}

class Ellipse(val xCen: Double, val yCen: Double, val xRight: Double, val yRight: Double, val upRadius: Double) extends EllipseLike with Transer//ffer
{ //type AlignT = Ellipse
  override def fTrans(f: Vec2 => Vec2): EllipseLike = ???
  def vCen: Vec2 = xCen vv yCen
  def vRight: Vec2 = xRight vv yRight
  override def shear(xScale: Double, yScale: Double): Ellipse = this
  override def rotate(angle: Angle): Ellipse = this
  override def rotateRadians(radians: Double):  Ellipse = this
  override def slate(offset: Vec2): Ellipse = Ellipse(vCen + offset, vRight + offset, upRadius)
  override def scale(operand: Double):  Ellipse = Ellipse(vCen * operand * operand, vRight * operand, upRadius * operand)
  override def mirror(line: Line2): Ellipse = Ellipse(vCen.mirror(line), vRight.mirror(line), upRadius)
}

object Ellipse
{ def apply(vCen: Vec2, vRight: Vec2, upRadius: Double): Ellipse = new Ellipse(vCen.x, vCen.y, vRight.x, vRight.y, upRadius)
}