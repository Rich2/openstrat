/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareGen]], a general square. */
trait Square extends Rectangle
{ //def width: Double
  //def height: Double = width

  /** Translate geometric transformation on a Square returns a Square. */
  override def slate(offset: Vec2): Square = Square.cenV0(cen + offset, v0 + offset)

  /** Translate geometric transformation on a Square returns a Square. */
  override def slate(xOffset: Double, yOffset: Double): Square = Square.cenV0(cen.addXY(xOffset, yOffset), v0.addXY(xOffset, yOffset))

  /** Uniform scaling transformation on a Square returns a Square. */
  override def scale(operand: Double): Square = Square.cenV0(cen * operand, v0 * operand)

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negY: Square = Square.cenV0(cen.negY, v0.negY)

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negX: Square = Square.cenV0(cen.negX, v0.negX)

  override def prolign(matrix: ProlignMatrix): Square = Square.cenV0(cen.prolign(matrix), v0.prolign(matrix))

  override def reflect(lineLike: LineLike): Square = Square.cenV0(cen.reflect(lineLike), v0.reflect(lineLike))

  override def rotate(angle: Angle): Square = Square.cenV0(cen.rotate(angle), v0.rotate(angle))
}

/** Companion object for the Square trait. However its apply methods delegate to the [[SquareImp]] implementation class. */
object Square extends ShapeIcon
{
  def apply(width: Double, rotation: Angle, cen: Vec2 = Vec2Z): Square =
  { val delta = Vec2(width / 2, width / 2).rotate(rotation)
    cenV0(cen, cen + delta)
  }
  
  def apply(width: Double, rotation: Angle, xCen: Double, yCen: Double): Square =
  { val delta = Vec2(width / 2, width / 2).rotate(rotation)
    new SquareImp(xCen, yCen, xCen + delta.x, yCen + delta.y)
  }

  def cenV0(cen: Vec2, v0: Vec2): Square = new SquareImp(cen.x, cen.y, v0.x, v0.y)

  /** Scale the Square and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, xCen: Double, yCen: Double): Sqlign = Sqlign(scale, xCen, yCen)

  /** Scale the Shape and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, cen: Vec2): Shape = Sqlign(scale, cen)

  override def fill(colour: Colour): ShapeGraphicIcon = ???

  /** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
   *  rotated while remaining a Square. */
  final class SquareImp(val xCen: Double, val yCen: Double, val x0: Double, val y0: Double) extends Square with RectCenV0
  {
    //override def width: Double = v0.distTo(v1)
    override def v1: Vec2 = cen + (v0 - cen).rotate270
    override def x1: Double = v1.x
    override def y1: Double = v1.y
    override def attribs: Arr[XANumeric] = ???

    def rotationRadians: Double = rotation.radians
    @inline override def rotation: Angle =  sline0.angle + 90.degs // Angle.radians(rotationRadians)

    override def productArity: Int = 3
    override def productElement(n: Int): Any = 4
    override def toString: String = s"SquareClass($x0, $y0; $x1, $y1)"
    override def fTrans(f: Vec2 => Vec2): SquareImp = SquareImp.cenV0(f(cen), f(v0))

    override def slate(offset: Vec2): SquareImp = SquareImp.cenV0(cen + offset, v0 + offset)

    /** Translate geometric transformation. */
    @inline override def slate(xOffset: Double, yOffset: Double): SquareImp = SquareImp.cenV0(cen.addXY(xOffset, yOffset), v0.addXY(xOffset, yOffset))

    override def scale(operand: Double): SquareImp = SquareImp.cenV0(cen * operand, v0 * operand)

    override def negY: SquareImp = SquareImp.cenV0(cen.negY, v0.negY)

    override def negX: SquareImp = SquareImp.cenV0(cen.negX, v0.negX)

    override def prolign(matrix: ProlignMatrix): SquareImp = SquareImp.cenV0(cen.prolign(matrix), v0.prolign(matrix))

    override def rotate(angle: Angle): SquareImp = SquareImp.cenV0(cen.rotate(angle), v0.rotate(angle))

    override def reflect(lineLike: LineLike): SquareImp = SquareImp.cenV0(cen.reflect(lineLike), v0.reflect(lineLike))

    //override def fill(fillColour: Colour): ShapeFill = ???

    //override def draw(lineWidth: Double, lineColour: Colour): PolygonDraw = ???
  }

  /** Factory object for squares. */
  object SquareImp //extends ShapeIcon
  {
    def cenV0(cen: Vec2, v0: Vec2): SquareImp = new SquareImp(cen.x, cen.y, v0.x, v0.y)

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