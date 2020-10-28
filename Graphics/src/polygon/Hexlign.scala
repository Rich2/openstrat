/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class Hexlign(val dMin: Double, val xCen: Double, val yCen: Double) extends HexReg
{
  override def x1: Double = xCen + rMax / 2
  override def y1: Double = yCen + rMin
  @inline override def v1: Vec2 = Vec2(x1, y1)

  override def x2: Double = xCen + rMax
  override def y2: Double = yCen
  @inline override def v2: Vec2 = Vec2(x2, y2)

  override def x3: Double = xCen + rMax / 2
  override def y3: Double = yCen - rMin
  @inline override def v3: Vec2 = Vec2(x3, y3)

  override def x4: Double = xCen - rMax / 2
  override def y4: Double = yCen - rMin
  @inline override def v4: Vec2 = Vec2(x4, y4)

  override def x5: Double = xCen - rMax
  override def y5: Double = yCen
  @inline override def v5: Vec2 = Vec2(x5, y5)

  override def x6: Double = xCen - rMax / 2
  override def y6: Double = yCen + rMin
  @inline override def v6: Vec2 = Vec2(x6, y6)

  override def vert(index: Int): Vec2 = index match
  { case 1 => v1
    case 2 => v2
    case 3 => v3
    case 4 => v4
    case 5 => v5
    case 6 => v6
    case n => excep("$index is out of range for a Hexagon vertex")
  }
  
  override def s4Cen: Vec2 = Vec2(0, -rMin)
  override def s1Cen: Vec2 = Vec2(0, rMin)
  override def ptsArray: Array[Double] = ???

  override def xVertsArray: Array[Double] = Array(x1, x2, x3, x4, x5, x6)

  override def yVertsArray: Array[Double] = Array(y1, y2, y3, y4, y5, y6)

  override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x1, y1);  f(x2, y2); f(x3, y3);  f(x4, y4); f(x5, y5);  f(x6, y6) }

    /** Returns the X component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def xVert(index: Int): Double = ???

  /** Returns the Y component of the vertex of the given number. Will throw an exception if the vertex index is out of range. */
  override def yVert(index: Int): Double = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Vec2): Hexlign = Hexlign(dMin, cen + offset)

  /** Translate geometric transformation on a Hexlign returns a Hexlign. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(xOffset: Double, yOffset: Double): Hexlign = Hexlign(dMin, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes transformation on a Hexlign returning a Hexlign. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): Hexlign = Hexlign(dMin * operand, cen * operand)

  /** Mirror, reflection transformation of a Hexlign across the X axis, returns a Hexlign. */
  override def negY: Hexlign = Hexlign(dMin, cen.negY)

  /** Mirror, reflection transformation of Hexlign across the Y axis, returns a Hexlign. */
  override def negX: Hexlign = Hexlign(dMin, cen.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Hexlign, returns a Hexlign. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: Hexlign = Hexlign(dMin, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a Hexlign, returns a Hexlign. The return type will be narrowed in sub traits / classes. */
  override def rotate180: Hexlign = Hexlign(dMin, cen.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Hexlign, returns a Hexlign. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: Hexlign = Hexlign(dMin, cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): Hexlign = Hexlign(dMin, cen.prolign(matrix))
}

object Hexlign
{
  def apply(height: Double, cen: Vec2 = Vec2Z): Hexlign = new Hexlign(height, cen.x, cen.y)

  implicit val slateImplicit: Slate[Hexlign] = (obj: Hexlign, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Hexlign] = (obj: Hexlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[Hexlign] = (obj, matrix) => obj.prolign(matrix)
}