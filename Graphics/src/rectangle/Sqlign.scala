/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A square aligned to the X and Y axes. */
final case class Sqlign private(width: Double, xCen: Double, yCen: Double) extends Square with Rect
{
  override def attribs: Arr[XANumeric] = ???
  override def width1 = width
  override def width2: Double = width

  override def height: Double = width
  override def slate(offset: Vec2Like): Sqlign = Sqlign(width, cen + offset)

  override def slate(xOffset: Double, yOffset: Double): Sqlign = Sqlign(width, xCen + xOffset, yCen + yOffset)
  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen * operand)

  override def negY: Sqlign = Sqlign(width, xCen, -yCen)

  override def negX: Sqlign = Sqlign(width, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  @inline override def slateTo(newCen: Pt2): Sqlign = slate(cen.vecTo(newCen))
}

/** Factory object for Sqlign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Pt2 = Vec2Z): Sqlign = new Sqlign(width, cen.x, cen.y)
  def apply(width: Double, xCen: Double, yCen: Double): Sqlign = new Sqlign(width, xCen, yCen)
}