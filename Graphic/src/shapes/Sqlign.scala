/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A square aligned to the X and Y axes. */
final case class Sqlign(width: Double, xCen: Double, yCen: Double) extends GeomElem with Rectlign// extends Transer
{
  override def height: Double = width

  override def slate(offset: Vec2): Sqlign = Sqlign(width, cen + offset)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): Sqlign = Sqlign(width, xCen + xOffset, yCen + yOffset)

  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Sqlign = Sqlign(width, cen.mirrorXOffset(yOffset))

  override def mirrorX: Sqlign = Sqlign(width, xCen, -yCen)

  override def mirrorYOffset(xOffset: Double): Sqlign = Sqlign(width, cen.mirrorYOffset(xOffset))

  override def mirrorY: Sqlign = Sqlign(width, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolignTrans(matrix))
}

/** Factory object for Sqalign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Vec2): Sqlign = new Sqlign(width, cen.x, cen.y)
}
