/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
 *  rotated while remaining a Square. */
final class SquareGen private(val x0: Double, val y0: Double, val x1: Double, val y1: Double) extends Square with RectV0V1// with SimilarPreserve
{ 
  override def width: Double = v0.distTo(v1)
  override def shapeAttribs: Arr[XANumeric] = ???

  def rotationRadians: Double = rotation.radians
  @inline override def rotation: Angle =  sline0.angle + 90.degs // Angle.radians(rotationRadians)

  override def productArity: Int = 3
  override def productElement(n: Int): Any = 4
  override def toString: String = s"SquareClass($x0, $y0; $x1, $y1)"
  override def fTrans(f: Vec2 => Vec2): SquareGen = SquareGen.v0v1(f(v0), f(v1))

  override def slate(offset: Vec2): SquareGen = SquareGen(width, cen + offset)

  /** Translate geometric transformation. */
  @inline override def slate(xOffset: Double, yOffset: Double): SquareGen = SquareGen(width, xCen + xOffset, yCen + yOffset, rotation)

  override def scale(operand: Double): SquareGen = SquareGen(width * operand, cen * operand)

  override def reflectXOffset(yOffset: Double): SquareGen = SquareGen(width, cen.reflectXOffset(yOffset))

  override def reflectX: SquareGen = SquareGen(width, xCen, -yCen, rotation)

  override def reflectYOffset(xOffset: Double): SquareGen = SquareGen(width, cen.reflectYOffset(xOffset))

  override def reflectY: SquareGen = SquareGen.v0v1(v1.reflectY, v0.reflectY)

  override def prolign(matrix: ProlignMatrix): SquareGen = SquareGen(width * matrix.vFactor, cen.prolign(matrix), rotation)

  override def rotateRadians(radians: Double): SquareGen = ???

  override def reflect(line: Line): SquareGen = ???
  override def reflect(line: Sline): SquareGen = ???

  override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

  override def fillOld(fillColour: Colour): ShapeFillOld = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

/** Factory object for squares. */
object SquareGen extends ShapeIcon
{
  /*@inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle): Square = Square(width, cen.x, cen.y, rotation)

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): Square = Square(width, xCen, yCen, rotation)*/

  @inline def apply(width: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): SquareGen =
  { val v0 = cen + Vec2(width / 2, width / 2).rotate(rotation)
    val v1 = cen + Vec2(width / 2, -width / 2).rotate(rotation)
    new SquareGen(v0.x, v0.y, v1.x, v1.y)
  }

  @inline def apply(width: Double, xCen: Double, yCen: Double, rotation: Angle): SquareGen = apply(width, xCen vv yCen, rotation)

  def v0v1(v0: Vec2, v1: Vec2): SquareGen = new SquareGen(v0.x, v0.y, v1.x, v1.y)

  def xy(width: Double, xCen: Double, yCen: Double): PolygonGen = PolygonGen(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width / 2 vv yCen - width / 2)

  override def scaleSlate(scale: Double, cen: Vec2): Shape = ???

  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape = ???

  override def fill(colour: Colour): ShapeGraphicIcon = ???
}