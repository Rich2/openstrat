/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareGen]], a general square. */
trait Square extends Rectangle, SsDbl2[Pt2]
{ type ThisT <: Square
  override def typeStr: String = "Square"

  /** The width of this square. */
  def width: Double

  override def vertsTrans(f: Pt2 => Pt2): Rectangle// = Square.fromArray(arrayElemMap(f))
  def squareVertsTrans(f: Pt2 => Pt2): Square = Square.fromArray(arrayElemMap(f))
  override def slate(operand: VecPt2): Square = squareVertsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Square = squareVertsTrans(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): Square = squareVertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): Square = squareVertsTrans(_.slateY(yOperand))
  override def scale(operand: Double): Square = squareVertsTrans(_.scale(operand))
  override def negX: Square = squareVertsTrans(_.negX)
  override def negY: Square = squareVertsTrans(_.negY)
  override def rotate90: Square = squareVertsTrans(_.rotate90)
  override def rotate180: Square = squareVertsTrans(_.rotate180)
  override def rotate270: Square = squareVertsTrans(_.rotate270)
  override def prolign(matrix: ProlignMatrix): Square = squareVertsTrans(_.prolign(matrix))
  override def reflect(lineLike: LineLike): Square = squareVertsTrans(_.reflect(lineLike))
  override def rotate(rotation: AngleVec): Square = squareVertsTrans(_.rotate(rotation))
}

/** Companion object for the Square trait. However, its apply methods delegate to the [[SquareGen]] implementation class. */
object Square extends ShapeIcon
{
  override type ShapeT = Sqlign

  def fromArray(array: Array[Double]) = new SquareGen(array)

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the square's
   * scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with its centre at the
   * axes centre. Otherwise, the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre. If you want to create a
   * square aligned to the axes, then you are probably better using the [[Sqlign]] factory apply method. */
  def apply(width: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Square =
  { val rtVec = xVec2(width / 2).rotate(rotation)
    val upVec = yVec2(width / 2).rotate(rotation)
    val p0 = cen - rtVec + upVec
    val p1 = cen + rtVec + upVec
    val p2 = cen + rtVec - upVec
    val p3 = cen - rtVec - upVec
    val array: Array[Double] = Array[Double](p0.x, p0.y, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    new SquareGen(array)
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
  final class SquareGen(val arrayUnsafe: Array[Double]) extends Square, PolygonLikeDbl2[Pt2], Pt2SeqSpec
  { override type ThisT = SquareGen

    override def fromArray(array: Array[Double]): SquareGen = new SquareGen(array)

    override def vertsTrans(f: Pt2 => Pt2) = fromArray(arrayElemMap(f))
    @inline override def width: Double = width1
    @inline override def width2: Double = width1

    override def attribs: RArr[XmlAtt] = ???

    override def rotation: AngleVec = ???

    override def toString: String = s"SquareClass($v0x, $v0y; $v1x, $v1y)"

    override def cenX: Double = v0x \/ v2x
    override def cenY: Double = v0y \/ v2y

    override def v0x: Double = arrayUnsafe(0)
    override def v0y: Double = arrayUnsafe(1)
    override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
    override def vLastX: Double = arrayUnsafe(numVerts - 2)
    override def vLastY: Double = arrayUnsafe(numVerts - 1)
    override def vLast: Pt2 = Pt2(vLastX, vLastY)
    override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
    override def sd0CenX: Double = v0x \/ vertX(1)
    override def sd0CenY: Double = v0y \/ vertY(1)
    override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
    override def vertX(index: Int): Double = arrayUnsafe(index * 2)
    override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
    override def sides: LineSegArr = new LineSegArr(arrayForSides)
  }
}