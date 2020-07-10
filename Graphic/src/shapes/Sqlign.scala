/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A square aligned to the X and Y axes. */
final case class Sqlign(width: Double, xCen: Double, yCen: Double) extends TransElem with Rectanglelign// extends Transer
{ override type ThisT = Sqlign

  override def fTrans(f: Vec2 => Vec2): Sqlign = ???
  override def height: Double = width

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

  override def rotateRadians(radians: Double): Square = ???

  override def reflect(line: Line): Square = ???
  override def reflect(line: LineSeg): Square = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

  override def shearX(operand: Double): TransElem = ???

  override def fill(fillColour: Colour): ShapeFill = ???

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  override def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

/** Factory object for Sqalign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Vec2): Sqlign = new Sqlign(width, cen.x, cen.y)
}
