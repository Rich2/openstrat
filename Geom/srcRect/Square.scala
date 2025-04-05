/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareImp]], a general square. */
trait Square extends Rectangle
{ type ThisT <: Square
  override def typeStr: String = "Square"

  /** The width of this square. */
  def width: Double

  /** The roatation of this sqaure from alignment with the X and Y axes. */
  def rotation: AngleVec

  override def vertsTrans(f: Pt2 => Pt2): Square = Square.fromArray(arrayElemMap(f))
  override def slate(offset: VecPt2): Square = vertsTrans(_.slate(offset))
  override def slate(xOperand: Double, yOperand: Double): Square = vertsTrans(_.slate(xOperand, yOperand))
  override def scale(operand: Double): Square = vertsTrans(_.scale(operand))
  override def negX: Square = Square.fromArray(unsafeNegX)
  override def negY: Square = Square.fromArray(unsafeNegY)
  override def rotate90: Square = vertsTrans(_.rotate90)
  override def rotate180: Square = vertsTrans(_.rotate180)
  override def rotate270: Square = vertsTrans(_.rotate270)
  override def prolign(matrix: ProlignMatrix): Square = vertsTrans(_.prolign(matrix))
  override def reflect(lineLike: LineLike): Square = vertsTrans(_.reflect(lineLike))
  override def rotate(angle: AngleVec): Square = vertsTrans(_.rotate(angle))
}

/** Companion object for the Square trait. However its apply methods delegate to the [[SquareImp]] implementation class. */
object Square extends ShapeIcon
{
  override type ShapeT = Sqlign

  def fromArray(array: Array[Double]) = new SquareImp(array)

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the square's
   * scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with its centre at the
   * axes centre. Otherwise, the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre. If you want to create a
   * square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  def apply(width: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Square =
  { val rtVec = xVec2(width / 2).rotate(rotation)
    val upVec = yVec2(width / 2).rotate(rotation)
    val p0 = cen - rtVec + upVec
    val p1 = cen + rtVec + upVec
    val p2 = cen + rtVec - upVec
    val p3 = cen - rtVec - upVec
    val array: Array[Double] = Array[Double](p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    new SquareImp(array)
  }

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the square's
   * scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with its centre at the
   * axes' centre. Otherwise, the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre. If you want to create a
   * square aligned to the axes, then you are probably better using the Sqlign factory apply method. */
  def apply(width: Double, rotation: AngleVec, xCen: Double, yCen: Double): Square = apply(width, rotation, Pt2(xCen, yCen))

  /** Scale the Square and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, xCen: Double, yCen: Double): Sqlign = Sqlign(scale, xCen, yCen)

  /** Scale the Shape and position (translate) it. This method is equivalent to scaling the icon and then translating (repositioning) it. */
  override def reify(scale: Double, cen: Pt2 = Pt2Z): Sqlign = Sqlign(scale, cen)

  override def fill(colour: Colour): ShapeGraphicIcon = ???

  /** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and rotated while
   * remaining a Square. */
  final class SquareImp(val arrayUnsafe: Array[Double]) extends Square
  { override type ThisT = SquareImp

    override def fromArray(array: Array[Double]): SquareImp = new SquareImp(array)

    @inline override def width: Double = width1
    @inline override def width2: Double = width1

    override def attribs: RArr[XmlAtt] = ???

    override def rotation: AngleVec = ???

    override def toString: String = s"SquareClass($v0x, $v0y; $v1x, $v1y)"
  }
}