/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis. */
final class HexXlign(val dInner: Double, val xCen: Double, val yCen: Double) extends HexReg
{ override def cen: Pt2 = xCen pp yCen
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

  override def xSd1Cen: Double = xCen
  override def ySd1Cen: Double = yCen + rInner
  override def sd1Cen: Pt2 = xCen pp ySd1Cen

  override def xSd2Cen: Double = ???
  override def ySd2Cen: Double = ???
  override def sd2Cen: Pt2 = ???

  override def xSd4Cen: Double = xCen
  override def ySd4Cen: Double = yCen - rInner
  override def sd4Cen: Pt2 = xCen pp ySd4Cen

  override def productArity: Int = 3
  override def productElement(n: Int): Any = ???

  /** Translate geometric transformation on a HexYlign returns a HexYlign. The return type of this method will be narrowed  further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def xySlate(xOffset: Double, yOffset: Double): HexXlign = HexXlign(dInner, cen.addXY(xOffset, yOffset))

  /** Uniform scaling against both X and Y axes transformation on a HexYlign returning a HexYlign. Use the xyScale method for differential scaling. The
   * return type of this method will be narrowed further in descendant traits / classes. */
  override def scale(operand: Double): HexXlign = HexXlign(dInner * operand, cen.scale(operand))

  /** Mirror, reflection transformation of a HexYlign across the X axis, returns a HexYlign. */
  override def negY: HexXlign = HexXlign(dInner, cen.negY)

  /** Mirror, reflection transformation of HexYlign across the Y axis, returns a HexYlign. */
  override def negX: HexXlign = HexXlign(dInner, cen.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: HexYlign = HexYlign(dInner, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a HexYlign, returns a HexYlign. The return type will be narrowed in sub traits / classes. */
  override def rotate180: HexXlign = HexXlign(dInner, cen.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a HexYlign, returns a HexYlign. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: HexXlign = HexXlign(dInner, cen.rotate270)

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): HexXlign = HexXlign(dInner, cen.prolign(matrix))
}

object HexXlign
{
  def apply(height: Double, cen: Pt2 = Pt2Z): HexXlign = new HexXlign(height, cen.x, cen.y)
  def apply(height: Double, xCen: Double, yCen: Double): HexXlign = new HexXlign(height, xCen, yCen)

  implicit val slateImplicit: Slate[HexXlign] = (obj: HexXlign, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[HexXlign] = (obj: HexXlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[HexXlign] = (obj, matrix) => obj.prolign(matrix)
}