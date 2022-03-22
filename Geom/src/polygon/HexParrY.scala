/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Regular Hexagon where two of the sides are parallel to the Y Axis. This will be the standard Hex for the Tiling module. */
final class HexParrY(val unsafeArray: Array[Double]) extends Hexlign with Show2[Double, Pt2] with ElemDbl3
{ override type ThisT = HexParrY
  override def unsafeFromArray(array: Array[Double]): HexParrY = new HexParrY(array)

  override def typeStr = "HexParrY"
  def width: Double = ???

  override def name1: String = "width"
  override def name2: String = "cen"
  override def dbl1: Double = width
  override def dbl2: Double = cenX
  override def dbl3: Double = cenY
  override def diameterIn: Double = width
  override def height: Double = diameterOut
  override def show1: Double = width
  override def show2: Pt2 = cen
  override implicit def showT1: ShowT[Double] = ShowT.doublePersistEv
  override implicit def showT2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxDepth: Int = 3

  override def cen: Pt2 = cenX pp cenY
//  override def v0x: Double = cenX + radiusIn
//  override def v0y: Double = cenY + radiusOut / 2
//  @inline override def v0: Pt2 = Pt2(v0x, v0y)
//
//  override def v1x: Double = cenX + radiusIn
//  override def v1y: Double = cenY - radiusOut / 2
//  @inline override def v1: Pt2 = Pt2(v1x, v1y)
//
//  override def v2x: Double = cenX
//  override def v2y: Double = cenY - radiusOut
//  @inline override def v2: Pt2 = Pt2(v2x, v2y)
//
//  override def v3x: Double = cenX - radiusIn
//  override def v3y: Double = cenY - radiusOut / 2
//  @inline override def v3: Pt2 = Pt2(v3x, v3y)
//
//  override def sd0CenX: Double = cenX + radiusIn * Cos60
//  override def sd0CenY: Double = cenY + radiusIn * Sin60
//  override def sd0Cen: Pt2 = sd0CenX pp sd0CenY
//
//  override def sd1CenX: Double = cenX + radiusIn
//  override def sd1CenY: Double = cenY
//  override def sd1Cen: Pt2 = sd1CenX pp sd1CenY
//
//  override def sd2CenX: Double = cenX + radiusIn * Cos60
//  override def sd2CenY: Double = cenY - radiusIn * Sin60
//  override def sd2Cen: Pt2 = sd2CenX pp sd2CenY
//
//  override def sd3CenX: Double = cenX - radiusIn * Cos60
//  override def sd3CenY: Double = cenY - radiusIn * Sin60
//  override def sd3Cen: Pt2 = sd3CenX pp sd3CenY

//  override def sd4CenX: Double = cenX - radiusIn
//  override def sd4CenY: Double = cenY
//  override def sd4Cen: Pt2 = sd4CenX pp sd4CenY
//
//  override def sd5CenX: Double = cenX - radiusIn * Cos60
//  override def sd5CenY: Double = cenY + radiusIn * Sin60
//  override def sd5Cen: Pt2 = sd5CenX pp sd5CenY

  /** Translate 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def slateXY(xDelta: Double, yDelta: Double): HexParrY = HexParrY(diameterIn, cen.addXY(xDelta, yDelta))

  /** Uniform scaling against both X and Y axes 2D geometric transformation on this HexYlign returns a HexYlign. */
  override def scale(operand: Double): HexParrY = HexParrY(diameterIn * operand, cen.scale(operand))

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the X axis, negates Y, returns a HexYlign. */
  override def negY: HexParrY = HexParrY(diameterIn, cen.negY)

  /** Mirror, reflection 2D geometric transformation of this HexYlign across the Y axis, negates X, returns a HexYlign. */
  override def negX: HexParrY = HexParrY(diameterIn, cen.negX)

  /** Rotate 90 degrees positively or anti clockwise  2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 270 degrees
   *  clockwise. */
  override def rotate90: HexParrX = HexParrX(diameterIn, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. */
  override def rotate180: HexParrY = HexParrY(diameterIn, cen.rotate180)

  /** Rotate 270 degrees positively or anti clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. Equivalent to rotate 90 degrees
   *  clockwise. */
  override def rotate270: HexParrY = HexParrY(diameterIn, cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes on this HexTlign returns a HexYlign. */
  override def prolign(matrix: ProlignMatrix): HexParrY = HexParrY(diameterIn, cen.prolign(matrix))
}

/** Companion object for the regular hexagon aligned to the Y Axis class. It has a limited set of 2D geometric transformation type class instances as
 * the type can not be maintained through all affine transformations. */
object HexParrY
{
  def apply(width: Double, cen: Pt2 = Pt2Z): HexParrY = ???//new HexParrY(width, cen.x, cen.y)
  def apply(width: Double, xCen: Double, yCen: Double): HexParrY = ???//new HexParrY(width, xCen, yCen)

  /*override def v4x: Double = cenX - radiusIn
  override def v4y: Double = cenY + radiusOut / 2
  @inline override def v4: Pt2 = Pt2(v4x, v4y)*/


  /*override def v5x: Double = cenX
  override def y5: Double = cenY + radiusOut
  @inline override def v5: Pt2 = Pt2(v5x, y5)*/

  def unapply(input: HexParrY): Some[(Double, Pt2)] = Some((input.width, input.cen))

  implicit val persistImplicit: Persist[HexParrY] = Persist2[Double, Pt2, HexParrY]("HexYlign", "width", _.width,"cen", _.cen, apply)
  implicit val slateImplicit: Slate[HexParrY] = (obj: HexParrY, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[HexParrY] = (obj: HexParrY, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexParrY] = (obj, matrix) => obj.prolign(matrix)
}