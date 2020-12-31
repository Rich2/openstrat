/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the Y Axis. This will be the standard Hex for the Tiling module, */
final class HexYlign(val dInner: Double, val xCen: Double, val yCen: Double) extends HexReg
{ override def toString: String = "HexYlign".appendParenthSemis(xCen.str, yCen.str)
  override def cen: Pt2 = xCen pp yCen
  override def x1: Double = xCen + rInner
  override def y1: Double = yCen + rOuter / 2
  @inline override def v1: Pt2 = Pt2(x1, y1)

  override def x2: Double = xCen + rInner
  override def y2: Double = yCen - rOuter / 2
  @inline override def v2: Pt2 = Pt2(x2, y2)

  override def x3: Double = xCen
  override def y3: Double = yCen - rOuter
  @inline override def v3: Pt2 = Pt2(x3, y3)

  override def x4: Double = xCen - rInner
  override def y4: Double = yCen - rOuter / 2
  @inline override def v4: Pt2 = Pt2(x4, y4)

  override def x5: Double = xCen - rInner
  override def y5: Double = yCen + rOuter / 2
  @inline override def v5: Pt2 = Pt2(x5, y5)

  override def x6: Double = xCen
  override def y6: Double = yCen + rOuter
  @inline override def v6: Pt2 = Pt2(x6, y6)

  override def xSd1Cen: Double = xCen + rInner * Cos60
  override def ySd1Cen: Double = yCen + rInner * Sin60
  override def sd1Cen: Pt2 = xSd1Cen pp ySd1Cen

  override def xSd2Cen: Double = xCen + rInner
  override def ySd2Cen: Double = yCen
  override def sd2Cen: Pt2 = xSd2Cen pp ySd2Cen

  override def xSd3Cen: Double = xCen + rInner * Cos60
  override def ySd3Cen: Double = yCen - rInner * Sin60
  override def sd3Cen: Pt2 = xSd3Cen pp ySd3Cen

  override def xSd4Cen: Double = xCen - rInner * Cos60
  override def ySd4Cen: Double = yCen - rInner * Sin60
  override def sd4Cen: Pt2 = xSd4Cen pp ySd4Cen

  override def xSd5Cen: Double = xCen - rInner
  override def ySd5Cen: Double = yCen
  override def sd5Cen: Pt2 = xSd5Cen pp ySd5Cen

  override def xSd6Cen: Double = xCen - rInner * Cos60
  override def ySd6Cen: Double = yCen + rInner * Sin60
  override def sd6Cen: Pt2 = xSd6Cen pp ySd6Cen

  override def productArity: Int = 3
  override def productElement(n: Int): Any = ???

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def xySlate(xOffset: Double, yOffset: Double): HexYlign = HexYlign(dInner, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def scale(operand: Double): HexYlign = HexYlign(dInner * operand, cen.scale(operand))

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the X axis, negates Y, returns a HexYlign. */
  override def negY: HexYlign = HexYlign(dInner, cen.negY)

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the Y axis, negates X, returns a HexYlign. */
  override def negX: HexYlign = HexYlign(dInner, cen.negX)

  /** Rotate 90 degrees positively or anti clockwise  2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 270 degrees
   *  clockwise. */
  override def rotate90: HexXlign = HexXlign(dInner, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. */
  override def rotate180: HexYlign = HexYlign(dInner, cen.rotate180)

  /** Rotate 270 degrees positively or anti clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 90 degrees
   *  clockwise. */
  override def rotate270: HexYlign = HexYlign(dInner, cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes on this HexTlign returns a HexYlign. */
  override def prolign(matrix: ProlignMatrix): HexYlign = HexYlign(dInner, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the Y Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexYlign
{
  def apply(height: Double, cen: Pt2 = Pt2Z): HexYlign = new HexYlign(height, cen.x, cen.y)
  def apply(height: Double, xCen: Double, yCen: Double): HexYlign = new HexYlign(height, xCen, yCen)

  implicit val slateImplicit: Slate[HexYlign] = (obj: HexYlign, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[HexYlign] = (obj: HexYlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexYlign] = (obj, matrix) => obj.prolign(matrix)
}