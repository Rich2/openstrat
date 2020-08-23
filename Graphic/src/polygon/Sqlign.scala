/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A square aligned to the X and Y axes. */
final case class Sqlign private(width: Double, xCen: Double, yCen: Double) extends SquareTr with Rectanglelign
{ override type ThisT = Sqlign

  override def fTrans(f: Vec2 => Vec2): Sqlign = Sqlign.cenV0(f(cen), f(v0))

  override def slate(offset: Vec2): Sqlign = Sqlign(width, cen + offset)

  override def scale(operand: Double): Sqlign = Sqlign(width * operand, cen * operand)

  override def reflectXOffset(yOffset: Double): Sqlign = Sqlign(width, cen.reflectXOffset(yOffset))

  override def reflectX: Sqlign = Sqlign(width, xCen, -yCen)

  override def reflectYOffset(xOffset: Double): Sqlign = Sqlign(width, cen.reflectYOffset(xOffset))

  override def reflectY: Sqlign = Sqlign(width, -xCen, yCen)

  override def prolign(matrix: ProlignMatrix): Sqlign = Sqlign(width * matrix.vFactor, cen.prolign(matrix))

  override def rotate90: Sqlign = Sqlign(width, cen.rotate90)

  override def rotate180: Sqlign = Sqlign(width, cen.rotate180)

  override def rotate270: Sqlign = Sqlign(width, cen.rotate270)

  override def rotateRadians(radians: Double): Square = Square.v0v1(v0.rotateRadians(radians), v1.rotateRadians(radians))

  override def reflect(line: Line): Square = ???
  override def reflect(line: Sline): Square = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

  override def fillOld(fillColour: Colour): ShapeFill = ???

  override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

/** Factory object for Sqalign class. A square aligned to the X and Y axes. */
object Sqlign
{ def apply(width: Double, cen: Vec2): Sqlign = new Sqlign(width, cen.x, cen.y)
  def cenV0(cen: Vec2, v0: Vec2): Sqlign = new Sqlign((v0.x - cen.x) * 2, cen.x, cen.y)
}