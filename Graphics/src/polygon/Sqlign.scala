/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A square aligned to the X and Y axes. */
final case class Sqlign private(width: Double, xCen: Double, yCen: Double) extends Square with Rect
{ type ThisT = Sqlign
  override def attribs: Arr[XANumeric] = ???
  override def fTrans(f: Vec2 => Vec2): Sqlign = Sqlign.cenV0(f(cen), f(v0))

  override def slate(offset: Vec2): Sqlign = Sqlign(width, cen + offset)

  override def slate(xOffset: Double, yOffset: Double): Sqlign = ???
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen * operand)

  override def negY: Sqlign = Sqlign(width, xCen, -yCen)

  override def negX: Sqlign = Sqlign(width, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  //override def rotateRadians(radians: Double): SquareGen = SquareGen.v0v1(v0.rotateRadians(radians), v1.rotateRadians(radians))

  

  override def xyScale(xOperand: Double, yOperand: Double): Sqlign = ???

  //override def fill(fillColour: Colour): ShapeFill = ???

 // override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

/** Factory object for Sqalign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Vec2): Sqlign = new Sqlign(width, cen.x, cen.y)
  def cenV0(cen: Vec2, v0: Vec2): Sqlign = new Sqlign((v0.x - cen.x) * 2, cen.x, cen.y)
}