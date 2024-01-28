/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Utility object, contains various methods for creating crosses. */
object Cross
{ /** apply method ofr square cross with a width and height of the scale parameter. */
  def apply(cen: Pt2 = Pt2Z, scale: Double = 10): LineSegArr = apply(cen.x, cen.y, scale)

  /** apply method for square cross with a width and height of the scale parameter. */
  def apply(cenX: Double, cenY: Double, scale: Double): LineSegArr =
  { val delta = scale / 2
    LineSegArr.tuple4s((cenX - delta, cenY, cenX + delta, cenY), (cenX, cenY - delta, cenX, cenY + delta))
  }

  /** Draws a square cross with a width and height of the scale parameter. */
  def draw(cen: Pt2 = Pt2Z, scale: Double = 10, lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw =
    apply(cen.x, cen.y, scale).draw(lineWidth, colour)

  /** Draws a square cross with a width and height of the scale parameter. */
  def draw(cenX: Double, cenY: Double, scale: Double, lineWidth: Double, colour: Colour): LinesDraw =
    apply(cenX, cenY, scale).draw(lineWidth, colour)

  /** Diagonal cross with a width and height of the scale parameter. */
  def diag(cen: Pt2 = Pt2Z, scale: Double = 10): LineSegArr = diag(cen.x, cen.y, scale)

  /** Diagonal cross with a width and height of the scale parameter.. */
  def diag(cenX: Double, cenY: Double, scale: Double): LineSegArr =
  { val delta = scale / 2
    LineSegArr.tuple4s((cenX - delta, cenY - delta, cenX + delta, cenY + delta), (cenX - delta, cenY + delta, cenX + delta, cenY - delta))
  }

  /** Draws a diagonal cross with a width and height of the scale parameter. */
  def diagDraw(cen: Pt2 = Pt2Z, scale: Double = 10, lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw =
    diag(cen.x, cen.y, scale).draw(lineWidth, colour)

  /** Draws a diagonal cross with a width and height of the scale parameter. */
  def diagDraw(cenX: Double, cenY: Double, scale: Double, lineWidth: Double, colour: Colour): LinesDraw =
    diag(cenX, cenY, scale).draw(lineWidth, colour)
}