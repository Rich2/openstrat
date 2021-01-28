/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the Y Axis. This will be the standard Hex for the Tiling module. */
final class HexYlign(val width: Double, val xCen: Double, val yCen: Double) extends Hexlign with Show2[Double, Pt2]// with Eq2[Double, Pt2]
{ override def typeStr = "HexYlign"
  override def name1: String = "width"
  override def name2: String = "cen"
  override def diameterIn: Double = width
  override def height: Double = diameterOut
  override def el1: Double = width
  override def el2: Pt2 = cen
  override implicit def ev1: ShowT[Double] = ShowT.doublePersistImplicit
  override implicit def ev2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxdepth: Int = 3

  override def cen: Pt2 = xCen pp yCen
  override def x1: Double = xCen + radiusIn
  override def y1: Double = yCen + radiusOut / 2
  @inline override def v1: Pt2 = Pt2(x1, y1)

  override def x2: Double = xCen + radiusIn
  override def y2: Double = yCen - radiusOut / 2
  @inline override def v2: Pt2 = Pt2(x2, y2)

  override def x3: Double = xCen
  override def y3: Double = yCen - radiusOut
  @inline override def v3: Pt2 = Pt2(x3, y3)

  override def x4: Double = xCen - radiusIn
  override def y4: Double = yCen - radiusOut / 2
  @inline override def v4: Pt2 = Pt2(x4, y4)

  override def x5: Double = xCen - radiusIn
  override def y5: Double = yCen + radiusOut / 2
  @inline override def v5: Pt2 = Pt2(x5, y5)

  override def x6: Double = xCen
  override def y6: Double = yCen + radiusOut
  @inline override def v6: Pt2 = Pt2(x6, y6)

  override def xSd1Cen: Double = xCen + radiusIn * Cos60
  override def ySd1Cen: Double = yCen + radiusIn * Sin60
  override def sd1Cen: Pt2 = xSd1Cen pp ySd1Cen

  override def xSd2Cen: Double = xCen + radiusIn
  override def ySd2Cen: Double = yCen
  override def sd2Cen: Pt2 = xSd2Cen pp ySd2Cen

  override def xSd3Cen: Double = xCen + radiusIn * Cos60
  override def ySd3Cen: Double = yCen - radiusIn * Sin60
  override def sd3Cen: Pt2 = xSd3Cen pp ySd3Cen

  override def xSd4Cen: Double = xCen - radiusIn * Cos60
  override def ySd4Cen: Double = yCen - radiusIn * Sin60
  override def sd4Cen: Pt2 = xSd4Cen pp ySd4Cen

  override def xSd5Cen: Double = xCen - radiusIn
  override def ySd5Cen: Double = yCen
  override def sd5Cen: Pt2 = xSd5Cen pp ySd5Cen

  override def xSd6Cen: Double = xCen - radiusIn * Cos60
  override def ySd6Cen: Double = yCen + radiusIn * Sin60
  override def sd6Cen: Pt2 = xSd6Cen pp ySd6Cen

  override def productArity: Int = 3
  override def productElement(n: Int): Any = ???

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slateXY(xOffset: Double, yOffset: Double): HexYlign = HexYlign(diameterIn, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def scale(operand: Double): HexYlign = HexYlign(diameterIn * operand, cen.scale(operand))

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the X axis, negates Y, returns a HexYlign. */
  override def negY: HexYlign = HexYlign(diameterIn, cen.negY)

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the Y axis, negates X, returns a HexYlign. */
  override def negX: HexYlign = HexYlign(diameterIn, cen.negX)

  /** Rotate 90 degrees positively or anti clockwise  2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 270 degrees
   *  clockwise. */
  override def rotate90: HexXlign = HexXlign(diameterIn, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. */
  override def rotate180: HexYlign = HexYlign(diameterIn, cen.rotate180)

  /** Rotate 270 degrees positively or anti clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 90 degrees
   *  clockwise. */
  override def rotate270: HexYlign = HexYlign(diameterIn, cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes on this HexTlign returns a HexYlign. */
  override def prolign(matrix: ProlignMatrix): HexYlign = HexYlign(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the Y Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexYlign
{
  def apply(width: Double, cen: Pt2 = Pt2Z): HexYlign = new HexYlign(width, cen.x, cen.y)
  def apply(width: Double, xCen: Double, yCen: Double): HexYlign = new HexYlign(width, xCen, yCen)
  def unapply(input: HexYlign): Some[(Double, Pt2)] = Some((input.width, input.cen))

  implicit val persistImplicit: Persist[HexYlign] =
    new Persist2[Double, Pt2, HexYlign]("HexYlign", "width", _.width,"cen", _.cen, apply)

  implicit val slateImplicit: Slate[HexYlign] = (obj: HexYlign, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexYlign] = (obj: HexYlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexYlign] = (obj, matrix) => obj.prolign(matrix)
}