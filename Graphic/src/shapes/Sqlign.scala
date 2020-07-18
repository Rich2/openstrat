/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A square aligned to the X and Y axes. */
final case class Sqlign(width: Double, xCen: Double, yCen: Double) extends Square with Rectanglelign
{ override type ThisT = Sqlign

  override def fTrans(f: Vec2 => Vec2): Sqlign = ???

  override def slate(offset: Vec2): Sqlign = Sqlign(width, cen + offset)

  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): Sqlign = Sqlign(width, cen.mirrorXOffset(yOffset))

  override def mirrorX: Sqlign = Sqlign(width, xCen, -yCen)

  override def mirrorYOffset(xOffset: Double): Sqlign = Sqlign(width, cen.mirrorYOffset(xOffset))

  override def mirrorY: Sqlign = Sqlign(width, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  override def rotate90: Sqlign = Sqlign(width, cen.rotate90)

  override def rotate180: Sqlign = Sqlign(width, cen.rotate180)

  override def rotate270: Sqlign = Sqlign(width, cen.rotate270)

  override def rotateRadians(radians: Double): SquareClass = ???

  override def reflect(line: Line): SquareClass = ???
  override def reflect(line: Sline): SquareClass = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

}

/** Factory object for Sqalign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Vec2): Sqlign = new Sqlign(width, cen.x, cen.y)
}
