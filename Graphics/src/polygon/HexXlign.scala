/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexXlign(val height: Double, val xCen: Double, val yCen: Double) extends Hexlign with Show2[Double, Pt2]
{ override def typeStr = "HexXlign"
  override def name1: String = "height"
  override def name2: String = "cen"
  override def diameterIn: Double = height
  override def width: Double = diameterOut
  override def arg1: Double = height
  override def arg2: Pt2 = cen
  override implicit def ev1: ShowT[Double] = ShowT.doublePersistImplicit
  override implicit def ev2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxdepth: Int = 3

  override def cen: Pt2 = xCen pp yCen

  override def x1: Double = xCen + radiusOut / 2
  override def y1: Double = yCen + radiusIn
  @inline override def v1: Pt2 = Pt2(x1, y1)

  override def x2: Double = xCen + radiusOut
  override def y2: Double = yCen
  @inline override def v2: Pt2 = Pt2(x2, y2)

  override def x3: Double = xCen + radiusOut / 2
  override def y3: Double = yCen - radiusIn
  @inline override def v3: Pt2 = Pt2(x3, y3)

  override def x4: Double = xCen - radiusOut / 2
  override def y4: Double = yCen - radiusIn
  @inline override def v4: Pt2 = Pt2(x4, y4)

  override def x5: Double = xCen - radiusOut
  override def y5: Double = yCen
  @inline override def v5: Pt2 = Pt2(x5, y5)

  override def x6: Double = xCen - radiusOut / 2
  override def y6: Double = yCen + radiusIn
  @inline override def v6: Pt2 = Pt2(x6, y6)

  override def xSd1Cen: Double = xCen
  override def ySd1Cen: Double = yCen + radiusIn
  override def sd1Cen: Pt2 = xSd1Cen pp ySd1Cen

  override def xSd2Cen: Double = xCen + radiusIn * Cos30
  override def ySd2Cen: Double = yCen + radiusIn * Sin30
  override def sd2Cen: Pt2 = xSd2Cen pp ySd2Cen

  override def xSd3Cen: Double = xCen + radiusIn * Cos30
  override def ySd3Cen: Double = yCen - radiusIn * Sin30
  override def sd3Cen: Pt2 = xSd3Cen pp ySd3Cen

  override def xSd4Cen: Double = xCen
  override def ySd4Cen: Double = yCen - radiusIn
  override def sd4Cen: Pt2 = xSd4Cen pp ySd4Cen

  override def xSd5Cen: Double = xCen - radiusIn * Cos30
  override def ySd5Cen: Double = yCen - radiusIn * Sin30
  override def sd5Cen: Pt2 = xSd5Cen pp ySd5Cen
  override def xSd6Cen: Double = xCen - radiusIn * Cos30
  override def ySd6Cen: Double = yCen + radiusIn * Sin30
  override def sd6Cen: Pt2 = xSd6Cen pp ySd6Cen

  override def productArity: Int = 3
  override def productElement(n: Int): Any = ???

  /** Translate 2D geometric transformation on this HexXlign returns a HexXlign. */
  override def slateXY(xOffset: Double, yOffset: Double): HexXlign = HexXlign(diameterIn, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexXlign returning a HexXlign. */
  override def scale(operand: Double): HexXlign = HexXlign(diameterIn * operand, cen.scale(operand))

  /** Mirror, reflection 2D geometric transformation on this HexXlign across the X axis, negates Y, returns a HexXlign. */
  override def negY: HexXlign = HexXlign(diameterIn, cen.negY)

  /** Mirror, reflection 2D transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def negX: HexXlign = HexXlign(diameterIn, cen.negX)

  /** Rotate 90 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 270 degree negative or clock wise transformation. */
  override def rotate90: HexYlign = HexYlign(diameterIn, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a HexXlign. */
  override def rotate180: HexXlign = HexXlign(diameterIn, cen.rotate180)

  /** Rotate 270 degrees in a positive or clockwise direction 2D geometric transformation on this HexXlign across the Y axis, negates X, returns a
   *  HexYlign. Note the change in type. Equivalent to a 90 degree negative or clock wise transformation. */
  override def rotate270: HexYlign = HexYlign(diameterIn, cen.rotate270)

  /** Prolign 2d geometric transformations, similar transformations that retain alignment with the axes on this HexXlign returns a HexXlign. */
  override def prolign(matrix: ProlignMatrix): HexXlign = HexXlign(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the X Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexXlign
{ /** Apply factory method for HexXlign, Creates a regular hexagon with 2 of its side aligned to the X axis. */
  def apply(height: Double, cen: Pt2 = Pt2Z): HexXlign = new HexXlign(height, cen.x, cen.y)

  /** Apply factory method for [[HexXlign]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexXlign = new HexXlign(height, xCen, yCen)

  def unapply(input: HexXlign): Some[(Double, Pt2)] = Some((input.height, input.cen))

  implicit val persistImplicit: Persist[HexXlign] =
    new Persist2[Double, Pt2, HexXlign]("HexXlign", "height", _.height,"cen", _.cen, apply)

  implicit val slateImplicit: Slate[HexXlign] = (obj: HexXlign, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexXlign] = (obj: HexXlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexXlign] = (obj, matrix) => obj.prolign(matrix)
}