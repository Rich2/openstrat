/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareImp]], a general square. */
trait Square extends Rectangle
{
  override def s2Cen: Vec2 = v3 mid v2

  /** Translate geometric transformation on a Square returns a Square. */
  override def slate(offset: Vec2): Square = Square.cenV0(cen + offset, v1 + offset)

  /** Translate geometric transformation on a Square returns a Square. */
  override def slate(xOffset: Double, yOffset: Double): Square = Square.cenV0(cen.addXY(xOffset, yOffset), v1.addXY(xOffset, yOffset))

  /** Uniform scaling transformation on a Square returns a Square. */
  override def scale(operand: Double): Square = Square.cenV0(cen * operand, v1 * operand)

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negY: Square = Square.cenV0(cen.negY, v1.negY)

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negX: Square = Square.cenV0(cen.negX, v1.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Square, returns a Square. */
  override def rotate90: Square = Square.cenV0(cen.rotate90, v1.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a Square, returns a Square. */
  override def rotate180: Square = Square.cenV0(cen.rotate180, v1.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Square, returns a Square. */
  override def rotate270: Square = Square.cenV0(cen.rotate270, v1.rotate270)

  override def prolign(matrix: ProlignMatrix): Square = Square.cenV0(cen.prolign(matrix), v1.prolign(matrix))

  override def reflect(lineLike: LineLike): Square = Square.cenV0(cen.reflect(lineLike), v1.reflect(lineLike))

  override def rotate(angle: Angle): Square = Square.cenV0(cen.rotate(angle), v1.rotate(angle))

  override def slateTo(newCen: Vec2): Square = ???
}

/** Companion object for the Square trait. However its apply methods delegate to the [[SquareImp]] implementation class. */
object Square extends ShapeIcon
{
  override type ShapeT = Sqlign
  def s2s4(s2Cen: Vec2, s4Cen: Vec2): Square = new SquareImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y)

  def apply(width: Double, rotation: Angle, cen: Vec2 = Vec2Z): Square =
  { val s2 = cen + Vec2(width / 2, 0).rotate(rotation)
    val s4 = cen + Vec2(-width / 2, 0).rotate(rotation)
    s2s4(s2, s4)
  }
  
  def apply(width: Double, rotation: Angle, xCen: Double, yCen: Double): Square =
  { val s2 = Vec2(width / 2, 0).rotate(rotation).addXY(xCen, yCen)
    val s4 = Vec2(-width / 2, 0).rotate(rotation).addXY(xCen, yCen)
    s2s4(s2, s4)
  }

  def cenV0(cen: Vec2, v0: Vec2): Square = new SquareImp(cen.x, cen.y, v0.x, v0.y)

  /** Scale the Square and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, xCen: Double, yCen: Double): Sqlign = Sqlign(scale, xCen, yCen)

  /** Scale the Shape and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, cen: Vec2 = Vec2Z): Sqlign = Sqlign(scale, cen)

  override def fill(colour: Colour): ShapeGraphicIcon = ???

  /** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
   *  rotated while remaining a Square. */
  final class SquareImp(val xS2Cen: Double, val yS2Cen: Double, val xS4Cen: Double, val yS4Cen: Double) extends Square with RectS2S4
  {
    @inline override def width2: Double = width1

    override def attribs: Arr[XANumeric] = ???

    override def productArity: Int = 3
    override def productElement(n: Int): Any = 4
    override def toString: String = s"SquareClass($x1, $y1; $x2, $y2)"
   // override def fTrans(f: Vec2 => Vec2): SquareImp = Square.s2s4(f(cen), f(v1))

    /*override def slate(offset: Vec2): SquareImp = SquareImp.cenV0(cen + offset, v1 + offset)

    /** Translate geometric transformation. */
    @inline override def slate(xOffset: Double, yOffset: Double): SquareImp = SquareImp.cenV0(cen.addXY(xOffset, yOffset), v1.addXY(xOffset, yOffset))

    override def scale(operand: Double): SquareImp = SquareImp.cenV0(cen * operand, v1 * operand)

    override def negY: SquareImp = SquareImp.cenV0(cen.negY, v1.negY)

    override def negX: SquareImp = SquareImp.cenV0(cen.negX, v1.negX)

    override def prolign(matrix: ProlignMatrix): SquareImp = SquareImp.cenV0(cen.prolign(matrix), v1.prolign(matrix))

    override def rotate(angle: Angle): SquareImp = SquareImp.cenV0(cen.rotate(angle), v1.rotate(angle))

    override def reflect(lineLike: LineLike): SquareImp = SquareImp.cenV0(cen.reflect(lineLike), v1.reflect(lineLike))*/

    //override def fill(fillColour: Colour): ShapeFill = ???

    //override def draw(lineWidth: Double, lineColour: Colour): PolygonDraw = ???
  }

  /** Factory object for squares. */
  object SquareImp //extends ShapeIcon
  {
    //def cenV0(cen: Vec2, v0: Vec2): SquareImp = new SquareImp(cen.x, cen.y, v0.x, v0.y)
    def s2s4(s2Cen: Vec2, s4Cen: Vec2): SquareImp = new SquareImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y)
    def xy(width: Double, xCen: Double, yCen: Double): PolygonImp = PolygonImp(
      xCen - width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen + width / 2,
      xCen + width / 2 vv yCen - width / 2,
      xCen - width / 2 vv yCen - width / 2)

   // override def scaleSlate(scale: Double, cen: Vec2): Shape = ???

   // override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Shape = ???

   // override def fill(colour: Colour): ShapeGraphicIcon = ???
  }
}