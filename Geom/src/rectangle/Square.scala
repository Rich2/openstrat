/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareImp]], a general square. */
trait Square extends Rectangle
{
  /** The width of this square. */
  def width: Double

  def rotation: AngleVec

  /** Translate geometric transformation on a Square returns a Square. */
  override def slate(offset: Vec2Like): Square = Square.s2s4(sd2Cen.slate(offset), sd4Cen.slate(offset))

  /** Translate geometric transformation on a Square returns a Square. */
  override def slateXY(xDelta: Double, yDelta: Double): Square = Square.s2s4(sd2Cen.addXY(xDelta, yDelta), sd4Cen.addXY(xDelta, yDelta))

  /** Uniform scaling transformation on a Square returns a Square. */
  override def scale(operand: Double): Square = Square.s2s4(sd2Cen.scale(operand), sd4Cen.scale(operand))

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negY: Square = Square.s2s4(sd2Cen.negY, sd4Cen.negY)

  /** Mirror, reflection transformation across the X axis on a Square, returns a Square. */
  override def negX: Square = Square.s2s4(sd2Cen.negX, sd4Cen.negX)

  override def rotate90: Square = ???//Square(width, rotation, xCen, yCen)
  override def rotate180: Square = ???
  override def rotate270: Square = ???

  override def prolign(matrix: ProlignMatrix): Square = Square.s2s4(sd2Cen.prolign(matrix), sd4Cen.prolign(matrix))

  override def reflect(lineLike: LineLike): Square = Square.s2s4(sd2Cen.reflect(lineLike), sd4Cen.reflect(lineLike))

  override def rotate(angle: AngleVec): Square = Square.s2s4(sd2Cen.rotate(angle), sd4Cen.rotate(angle))
}

/** Companion object for the Square trait. However its apply methods delegate to the [[SquareImp]] implementation class. */
object Square extends ShapeIcon
{
  override type ShapeT = Sqlign
  def s2s4(s2Cen: Pt2, s4Cen: Pt2): Square = new SquareImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y)

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the
   * square's scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with
   * its centre at the axes centre. Otherwise the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre.
   * If you want to create a square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  def apply(width: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Square =
  { val s2 = cen + xVec2(width / 2).rotate(rotation)
    val s4 = cen + xVec2(-width / 2).rotate(rotation)
    s2s4(s2, s4)
  }

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the
   * square's scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with
   * its centre at the axes centre. Otherwise the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre.
   * If you want to create a square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  def apply(width: Double, rotation: AngleVec, xCen: Double, yCen: Double): Square =
  { val s2 = Pt2(width / 2, 0).rotate(rotation).addXY(xCen, yCen)
    val s4 = Pt2(-width / 2, 0).rotate(rotation).addXY(xCen, yCen)
    s2s4(s2, s4)
  }

  /** Scale the Square and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, xCen: Double, yCen: Double): Sqlign = Sqlign(scale, xCen, yCen)

  /** Scale the Shape and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, cen: Pt2 = Pt2Z): Sqlign = Sqlign(scale, cen)

  override def fill(colour: Colour): ShapeGraphicIcon = ???

  /** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and
   *  rotated while remaining a Square. */
  final class SquareImp(val sd2CenX: Double, val sd2CenY: Double, val sd4CenX: Double, val sd4CenY: Double) extends Square with RectS2S4
  {
    @inline override def width: Double = width1
    @inline override def width2: Double = width1

    override def attribs: Arr[XANumeric] = ???

    /** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
     * thought of as vertex 2.5. */
    override def sd3CenX: Double = ???

    /** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
     * thought of as vertex 2.5. */
    override def sd3CenY: Double = ???

    override def rotation: AngleVec = ???

   // override def productArity: Int = 3
    //override def productElement(n: Int): Any = 4
    override def toString: String = s"SquareClass($v1x, $v1y; $v2x, $v2y)"
  }

  /** Factory object for squares. */
  object SquareImp
  { def s2s4(s2Cen: Pt2, s4Cen: Pt2): SquareImp = new SquareImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y)
  }
}