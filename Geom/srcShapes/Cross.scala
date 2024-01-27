/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This just a temporary start. */
object Cross
{
  /** Temporary start. */
  def apply(scale: Double = 1, cen: Pt2 = Pt2Z): RArr[LineSegDraw] =
  { val lh = LineSeg(-10 pp 0, 10 pp 0)
    val rh =  LineSeg(0 pp 10, 0 pp -10)
    LineSegArr(lh, rh).map(_.scale(scale).slate(cen).draw(lineWidth = 2))
  }
  /** Square cross with a width and height of 1. */
  def apply: LineSegArr = LineSegArr.tuple4s((-0.5, 0, 0.5, 0), (0, -0.5, 0, 0.5))

  def blah(scale: Double, cen: Pt2 = Pt2Z): LineSegArr = apply.scale(scale).slate(cen)

  def draw(scale: Double, cen: Pt2 = Pt2Z, lineWidth: Double = 2, colour: Colour = Colour.Black): RArr[LineSegDraw] = apply(scale, cen)

  /** Diagonal cross with a width and height of 1. */
  def diag: LineSegArr = LineSegArr.tuple4s((-0.5, -0.5, 0.5, 0.5), (-0.5, 0.5, 0.5, -0.5))
}