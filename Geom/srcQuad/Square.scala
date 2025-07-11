/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** the Square trait can either be a [[Sqlign]], an aligned square or a [[SquareGen]], a general square. */
trait Square extends Rectangle
{ type ThisT <: Square
  override def typeStr: String = "Square"

  /** The width of this square. */
  def width: Double

  override def vertsTrans(f: Pt2 => Pt2): Square = Square.from3(f(v0), f(v1), f(v2))
//  def squareVertsTrans(f: Pt2 => Pt2): Square = Square.fromArray(arrayElemMap(f))
  override def slate(operand: VecPt2): Square = vertsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Square = vertsTrans(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): Square = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): Square = vertsTrans(_.slateY(yOperand))
  override def scale(operand: Double): Square = vertsTrans(_.scale(operand))
  override def negX: Square = vertsTrans(_.negX)
  override def negY: Square = vertsTrans(_.negY)
  override def rotate90: Square = vertsTrans(_.rotate90)
  override def rotate180: Square = vertsTrans(_.rotate180)
  override def rotate270: Square = vertsTrans(_.rotate270)
  override def prolign(matrix: AxlignMatrix): Square = vertsTrans(_.prolign(matrix))
  override def reflect(lineLike: LineLike): Square = vertsTrans(_.reflect(lineLike))
  override def rotate(rotation: AngleVec): Square = vertsTrans(_.rotate(rotation))
}

/** Companion object for the Square trait. However, its apply methods delegate to the [[SquareGen]] implementation class. */
object Square extends ShapeIcon
{
  override type ShapeT = Sqlign

  //def fromArray(array: Array[Double]) = ???// new SquareGen(array)

  /** Factory method for the creation of [[[Square]]s in the general case where the square is not aligned to the X and Y axis. The method takes the square's
   * scalar width followed by its rotation specified in [[AngleVec]]. If no further arguments are supplied the square will positioned with its centre at the
   * axes centre. Otherwise, the rotation can be followed by a centre point [[Pt2]] or the X and Y positions of the square's centre. If you want to create a
   * square aligned to the axes, then you are probably better using the [[Sqlign]] factory apply method. */
  def apply(width: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Square = {
    val hw = width / 2
    val v0: Pt2 = Pt2(hw, hw).rotate(rotation)
    val v1: Pt2 = Pt2(hw, -hw).rotate(rotation)
    val v2: Pt2 = Pt2(-hw, -hw).rotate(rotation)
    new SquareGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
  }

  /** Factory method for constructing a [[Square]] from its first 3 vertices. */
  def from3(v0: Pt2, v1: Pt2, v2: Pt2): Square = new SquareGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)

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
}

/** The class for a generalised square. If you want a square aligned XY axes use [[Sqlign]]. The square can be translated, scaled, reflected and rotated while
 * remaining a Square. */
final class SquareGen (val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends Square
{ override type ThisT = SquareGen
  override def vertsTrans(f: Pt2 => Pt2): SquareGen = SquareGen.from3(f(v0), f(v1), f(v2))
  @inline override def width: Double = width1
  @inline override def width2: Double = width1
  override def attribs: RArr[XHAtt] = ???
  override def rotation: AngleVec = ???
  override def toString: String = s"SquareClass($v0x, $v0y; $v1x, $v1y)"
}

object SquareGen
{ /** Factory method to create [[Square]] from the first 3 vertices. */
  def from3(v0: Pt2, v1: Pt2, v2: Pt2): SquareGen = new SquareGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
}