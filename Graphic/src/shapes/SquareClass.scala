/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Square extends Rectangle
{ def height: Double = width
}

/** Square can be translated, scaled, reflected and rotated while remaining a Square. */
final case class SquareClass(width: Double, xCen: Double, yCen: Double, rotation: Angle) extends Square
{
  override type ThisT = SquareClass
  def rotationRadians: Double = rotation.radians

  override def x0: Double = ???

  override def y0: Double = ???

  override def x1: Double = ???

  override def y1: Double = ???

  override def height: Double = ???

  override def xTopLeft: Double = ???

  override def yTopLeft: Double = ???

  override def topLeft: Vec2 = ???

  override def xTopRight: Double = ???

  override def yTopRight: Double = ???

  override def topRight: Vec2 = ???

  override def xBottomRight: Double = ???

  override def yBottomRight: Double = ???

  override def bottomRight: Vec2 = ???

  override def xBottomLeft: Double = ???

  override def yBottomLeft: Double = ???

  override def bottomLeft: Vec2 = ???

  override def fTrans(f: Vec2 => Vec2): SquareClass = ???

  override def slate(offset: Vec2): SquareClass = SquareClass(width, cen + offset)

  /** Translate geometric transformation. */
  @inline override def slate(xOffset: Double, yOffset: Double): SquareClass = SquareClass(width, xCen + xOffset, yCen + yOffset, rotation)

  override def scale(operand: Double): SquareClass = SquareClass(width * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): SquareClass = SquareClass(width, cen.mirrorXOffset(yOffset))

  override def mirrorX: SquareClass = SquareClass(width, xCen, -yCen, rotation)

  override def mirrorYOffset(xOffset: Double): SquareClass = SquareClass(width, cen.mirrorYOffset(xOffset))

  override def mirrorY: SquareClass = SquareClass(width, -xCen, yCen, rotation)

  override def prolign(matrix: ProlignMatrix): SquareClass = SquareClass(width * matrix.vFactor, cen.prolign(matrix), rotation)

  /** Rotates 90 degrees rotate-clockwise or + Pi/2 */
  override def rotate90: SquareClass = SquareClass(width, cen.rotate90)

  override def rotate180: SquareClass = SquareClass(width, cen.rotate180)

  override def rotate270: SquareClass = SquareClass(width, cen.rotate270)

  override def rotateRadians(radians: Double): SquareClass = ???

  override def reflect(line: Line): SquareClass = ???
  override def reflect(line: LineSeg): SquareClass = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}

/** Factory object for squares. */
object SquareClass extends ShapeIcon
{
 // def apply(width: Double, xCen: Double, yCen: Double, rotationRadians: Double): Square = new Square(width, xCen, yCen, rotationRadians)
  def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): SquareClass = new SquareClass(width, cen.x, cen.y, rotation)
  
  def xy(width: Double, xCen: Double, yCen: Double): PolygonClass = PolygonClass(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width/2   vv yCen - width / 2)

  override def scaleSlate(scale: Double, cen: Vec2): Shape = ???

  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape = ???

  override def fill(colour: Colour): ShapeGraphicIcon = ???
  
}