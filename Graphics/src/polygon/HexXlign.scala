/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class HexXlign(val dInner: Double, val xCen: Double, val yCen: Double) extends HexReg
{ override def cen: Pt2 = xCen pp yCen

  override def x1: Double = xCen + rOuter / 2
  override def y1: Double = yCen + rInner
  @inline override def v1: Pt2 = Pt2(x1, y1)

  override def x2: Double = xCen + rOuter
  override def y2: Double = yCen
  @inline override def v2: Pt2 = Pt2(x2, y2)

  override def x3: Double = xCen + rOuter / 2
  override def y3: Double = yCen - rInner
  @inline override def v3: Pt2 = Pt2(x3, y3)

  override def x4: Double = xCen - rOuter / 2
  override def y4: Double = yCen - rInner
  @inline override def v4: Pt2 = Pt2(x4, y4)

  override def x5: Double = xCen - rOuter
  override def y5: Double = yCen
  @inline override def v5: Pt2 = Pt2(x5, y5)

  override def x6: Double = xCen - rOuter / 2
  override def y6: Double = yCen + rInner
  @inline override def v6: Pt2 = Pt2(x6, y6)

  override def xSd1Cen: Double = xCen
  override def ySd1Cen: Double = yCen + rInner
  override def sd1Cen: Pt2 = xSd1Cen pp ySd1Cen

  override def xSd2Cen: Double = xCen + rInner * Cos30
  override def ySd2Cen: Double = yCen + rInner * Sin30
  override def sd2Cen: Pt2 = xSd2Cen pp ySd2Cen

  override def xSd3Cen: Double = xCen + rInner * Cos30
  override def ySd3Cen: Double = yCen - rInner * Sin30
  override def sd3Cen: Pt2 = xSd3Cen pp ySd3Cen

  override def xSd4Cen: Double = xCen - rInner * Sin30
  override def ySd4Cen: Double = yCen - rInner * Cos30
  override def sd4Cen: Pt2 = xSd4Cen pp ySd4Cen

  override def productArity: Int = 3
  override def productElement(n: Int): Any = ???

  /** Translate geometric transformation on a Hexlign returns a Hexlign. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def xySlate(xOffset: Double, yOffset: Double): HexXlign = HexXlign(dInner, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes transformation on a Hexlign returning a Hexlign. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexXlign = HexXlign(dInner * operand, cen.scale(operand))

  /** Mirror, reflection transformation of a Hexlign across the X axis, returns a Hexlign. */
  override def negY: HexXlign = HexXlign(dInner, cen.negY)

  /** Mirror, reflection transformation of Hexlign across the Y axis, returns a Hexlign. */
  override def negX: HexXlign = HexXlign(dInner, cen.negX)

  override def rotate90: HexYlign = HexYlign(dInner, cen.rotate90)
  override def rotate180: HexXlign = HexXlign(dInner, cen.rotate180)
  override def rotate270: HexYlign = HexYlign(dInner, cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexXlign = HexXlign(dInner, cen.prolign(matrix))
}

object HexXlign
{ /** Apply factory method for HexYlign, Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, cen: Pt2 = Pt2Z): HexXlign = new HexXlign(height, cen.x, cen.y)

  /** Apply factory method for [[HexXlign]], Creates a regular hexagon with 2 of its side aligned to the Y axis. */
  def apply(height: Double, xCen: Double, yCen: Double): HexXlign = new HexXlign(height, xCen, yCen)

  implicit val slateImplicit: Slate[HexXlign] = (obj: HexXlign, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[HexXlign] = (obj: HexXlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexXlign] = (obj, matrix) => obj.prolign(matrix)
}