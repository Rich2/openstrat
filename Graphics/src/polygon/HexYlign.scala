/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexYlign(val dMin: Double, val xCen: Double, val yCen: Double) extends HexReg
{
  override def x1: Double = xCen + rMin
  override def y1: Double = yCen + rMax / 2
  @inline override def v1: Pt2 = Pt2(x1, y1)

  override def x2: Double = xCen + rMin
  override def y2: Double = yCen - rMax / 2
  @inline override def v2: Pt2 = Pt2(x2, y2)

  override def x3: Double = xCen
  override def y3: Double = yCen - rMax
  @inline override def v3: Pt2 = Pt2(x3, y3)

  override def x4: Double = xCen - rMin
  override def y4: Double = yCen - rMax / 2
  @inline override def v4: Pt2 = Pt2(x4, y4)

  override def x5: Double = xCen - rMin
  override def y5: Double = yCen + rMax / 2
  @inline override def v5: Pt2 = Pt2(x5, y5)

  override def x6: Double = xCen
  override def y6: Double = yCen + rMax
  @inline override def v6: Pt2 = Pt2(x6, y6)
  
  override def s4Cen: Pt2 = Pt2(-rMin, 0)
  override def s1Cen: Pt2 = Pt2(rMin, 0)

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Pt2): HexYlign = HexYlign(dMin, cen + offset)

  /** Translate geometric transformation on a HexYlign returns a HexYlign. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(xOffset: Double, yOffset: Double): HexYlign = HexYlign(dMin, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes transformation on a HexYlign returning a HexYlign. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexYlign = HexYlign(dMin * operand, cen * operand)

  /** Mirror, reflection transformation of a HexYlign across the X axis, returns a HexYlign. */
  override def negY: HexYlign = HexYlign(dMin, cen.negY)

  /** Mirror, reflection transformation of HexYlign across the Y axis, returns a HexYlign. */
  override def negX: HexYlign = HexYlign(dMin, cen.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. The return type
   * will be narrowed in sub traits / classes. */
  /*override def rotate90: HexYlign = HexYlign(dMin, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. The return type will be narrowed in sub traits / classes. */
  override def rotate180: HexYlign = HexYlign(dMin, cen.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: HexYlign = HexYlign(dMin, cen.rotate270)*/

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexYlign = HexYlign(dMin, cen.prolign(matrix))
}

object HexYlign
{
  def apply(height: Double, cen: Pt2 = Vec2Z): HexYlign = new HexYlign(height, cen.x, cen.y)
  def apply(height: Double, xCen: Double, yCen: Double): HexYlign = new HexYlign(height, xCen, yCen)

  implicit val slateImplicit: Slate[HexYlign] = (obj: HexYlign, offset: Pt2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[HexYlign] = (obj: HexYlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexYlign] = (obj, matrix) => obj.prolign(matrix)
}