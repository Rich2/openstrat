/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Square extends Rectangle
{ def height: Double = width
}

/** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
 *  rotated while remaining a Square. */
final class SquareClass private(val x0: Double, val y0: Double, val x1: Double, val y1: Double) extends Square
{
  override def v0: Vec2 = x0 vv y0
  override def v1: Vec2 = x1 vv y1
  def width: Double = v0.distTo(v1)
  def xCen: Double = ???
  def yCen: Double = ???
  def rotationSecs: Double = ???

  override type ThisT = SquareClass
  @inline override def rotation: Angle = Angle.secs(rotationSecs)
  def rotationRadians: Double = rotation.radians
  override def productArity: Int = 3
  override def productElement(n: Int): Any = 4

  @inline override def xTopLeft: Double = x3
  @inline override def yTopLeft: Double = y3
  @inline override def topLeft: Vec2 = v3
  @inline override def xTopRight: Double = x0
  @inline override def yTopRight: Double = y0
  @inline override def topRight: Vec2 = v0
  @inline override def xBottomRight: Double = x1
  @inline override def yBottomRight: Double = y1
  @inline override def bottomRight: Vec2 = v1
  @inline override def xBottomLeft: Double = x2
  @inline override def yBottomLeft: Double = y2
  @inline override def bottomLeft: Vec2 = v2
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
  @inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): SquareClass = new SquareClass(width, cen.x, cen.y, rotation.secs)

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): SquareClass = new SquareClass(width, xCen, yCen, rotation.secs)

  def xy(width: Double, xCen: Double, yCen: Double): PolygonClass = PolygonClass(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width/2   vv yCen - width / 2)

  override def scaleSlate(scale: Double, cen: Vec2): Shape = ???

  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape = ???

  override def fill(colour: Colour): ShapeGraphicIcon = ???
  
}