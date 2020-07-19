/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
 *  rotated while remaining a Square. */
final class SquareClass private(val x0: Double, val y0: Double, val x1: Double, val y1: Double) extends Square with SimilarPreserve
{ override type ThisT = SquareClass
  override def v0: Vec2 = x0 vv y0
  override def v1: Vec2 = x1 vv y1
  override def width: Double = v0.distTo(v1)
  override def cen: Vec2 = sline0.midPtToRight(width / 2)
  override def xCen: Double = cen.x
  override def yCen: Double = cen.y

  @inline def v2: Vec2 = sline0.endToRight(width)
  @inline def x2: Double = v2.x
  @inline def y2: Double = v2.y
  @inline def v3: Vec2 = sline0.startToRight(width)
  @inline def x3: Double = v3.x
  @inline def y3: Double = v3.y
  def rotationRadians: Double = rotation.radians
  @inline override def rotation: Angle =  sline0.angle + 90.degs // Angle.radians(rotationRadians)

  override def productArity: Int = 3
  override def productElement(n: Int): Any = 4
  override def toString: String = s"SquareClass($x0, $y0; $x1, $y1)"
  override def fTrans(f: Vec2 => Vec2): SquareClass = SquareClass.v0v1(f(v0), f(v1))

  override def slate(offset: Vec2): SquareClass = SquareClass(width, cen + offset)

  /** Translate geometric transformation. */
  @inline override def slate(xOffset: Double, yOffset: Double): SquareClass = SquareClass(width, xCen + xOffset, yCen + yOffset, rotation)

  override def scale(operand: Double): SquareClass = SquareClass(width * operand, cen * operand)

  override def mirrorXOffset(yOffset: Double): SquareClass = SquareClass(width, cen.mirrorXOffset(yOffset))

  override def mirrorX: SquareClass = SquareClass(width, xCen, -yCen, rotation)

  override def mirrorYOffset(xOffset: Double): SquareClass = SquareClass(width, cen.mirrorYOffset(xOffset))

  override def mirrorY: SquareClass = SquareClass.v0v1(v1.mirrorY, v0.mirrorY)

  override def prolign(matrix: ProlignMatrix): SquareClass = SquareClass(width * matrix.vFactor, cen.prolign(matrix), rotation)

  /* Rotates 90 degrees rotate-clockwise or + Pi/2
  override def rotate90: SquareClass = SquareClass(width, cen.rotate90)

  override def rotate180: SquareClass = SquareClass(width, cen.rotate180)

  override def rotate270: SquareClass = SquareClass(width, cen.rotate270)

  override def rotateRadians(radians: Double): SquareClass = ???
*/
  override def reflect(line: Line): SquareClass = ???
  override def reflect(line: Sline): SquareClass = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}

/** Factory object for squares. */
object SquareClass extends ShapeIcon
{
  @inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): SquareClass =
  { val v0 = cen + Vec2(width / 2, width / 2).rotate(rotation)
    val v1 = cen + Vec2(width / 2, -width / 2).rotate(rotation)
    new SquareClass(v0.x, v0.y, v1.x, v1.y)
  }

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): SquareClass = apply(width, xCen vv yCen, rotation)

  def v0v1(v0: Vec2, v1: Vec2): SquareClass = new SquareClass(v0.x, v0.y, v1.x, v1.y)

  def xy(width: Double, xCen: Double, yCen: Double): PolygonClass = PolygonClass(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width / 2 vv yCen - width / 2)

  override def scaleSlate(scale: Double, cen: Vec2): Shape = ???

  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape = ???

  override def fill(colour: Colour): ShapeGraphicIcon = ???
}