/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Regular Hexagon where two of the sides are parallel to the X Axis */
final class Hexlign(val dMin: Double, val xCen: Double, val yCen: Double) extends HexReg
{
  override def x1: Double = xCen + rMax / 2
  override def y1: Double = yCen + rMin
  @inline override def v1: Pt2 = Pt2(x1, y1)

  override def x2: Double = xCen + rMax
  override def y2: Double = yCen
  @inline override def v2: Pt2 = Pt2(x2, y2)

  override def x3: Double = xCen + rMax / 2
  override def y3: Double = yCen - rMin
  @inline override def v3: Pt2 = Pt2(x3, y3)

  override def x4: Double = xCen - rMax / 2
  override def y4: Double = yCen - rMin
  @inline override def v4: Pt2 = Pt2(x4, y4)

  override def x5: Double = xCen - rMax
  override def y5: Double = yCen
  @inline override def v5: Pt2 = Pt2(x5, y5)

  override def x6: Double = xCen - rMax / 2
  override def y6: Double = yCen + rMin
  @inline override def v6: Pt2 = Pt2(x6, y6)
  
  override def s4Cen: Pt2 = Pt2(0, -rMin)
  override def s1Cen: Pt2 = Pt2(0, rMin)

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  /** Translate geometric transformation on a HexReg returns a HexReg. The return type of this method will be narrowed further in most descendant
   * traits / classes. The exceptions being those classes where the centring of the geometry at the origin is part of the type. */
  override def slate(offset: Vec2Like): Hexlign = Hexlign(dMin, cen + offset)

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

  /** Prolign 2d transformations, similar transformations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): Hexlign = Hexlign(dMin, cen.prolign(matrix))
}

object Hexlign
{
  def apply(height: Double, cen: Pt2 = Vec2Z): Hexlign = new Hexlign(height, cen.x, cen.y)

  implicit val slateImplicit: Slate[Hexlign] = (obj: Hexlign, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[Hexlign] = (obj: Hexlign, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[Hexlign] = (obj, matrix) => obj.prolign(matrix)
}