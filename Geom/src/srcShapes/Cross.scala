/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Utility object, contains various methods for creating crosses. */
object Cross
{ /** apply method ofr square cross with a width and height of the scale parameter. */
  def apply(cen: Pt2 = Origin2, scale: Double = 10): LSeg2Arr = apply(cen.x, cen.y, scale)

  /** apply method for square cross with a width and height of the scale parameter. */
  def apply(cenX: Double, cenY: Double, scale: Double): LSeg2Arr =
  { val delta = scale / 2
    LSeg2Arr.tuple4s((cenX - delta, cenY, cenX + delta, cenY), (cenX, cenY - delta, cenX, cenY + delta))
  }

  /** Draws a square cross with a width and height of the scale parameter. */
  def draw(cen: Pt2 = Origin2, scale: Double = 10, lineWidth: Double = 2, colour: Colour = Colour.Black): LSeg2ArrDraw =
    apply(cen.x, cen.y, scale).draw(lineWidth, colour)

  /** Draws a square cross with a width and height of the scale parameter. */
  def draw(cenX: Double, cenY: Double, scale: Double, lineWidth: Double, colour: Colour): LSeg2ArrDraw =
    apply(cenX, cenY, scale).draw(lineWidth, colour)

  /** Diagonal cross with a width and height of the scale parameter. */
  def diag(cen: Pt2 = Origin2, scale: Double = 10): LSeg2Arr = diag(cen.x, cen.y, scale)

  /** Diagonal cross with a width and height of the scale parameter. */
  def diag(cenX: Double, cenY: Double, scale: Double): LSeg2Arr =
  { val delta = scale / 2
    LSeg2Arr.tuple4s((cenX - delta, cenY - delta, cenX + delta, cenY + delta), (cenX - delta, cenY + delta, cenX + delta, cenY - delta))
  }

  /** Draws a diagonal cross with a width and height of the scale parameter. */
  def diagDraw(cen: Pt2 = Origin2, scale: Double = 10, lineWidth: Double = 2, colour: Colour = Colour.Black): LSeg2ArrDraw =
    diag(cen.x, cen.y, scale).draw(lineWidth, colour)

  /** Draws a diagonal cross with a width and height of the scale parameter. */
  def diagDraw(cenX: Double, cenY: Double, scale: Double, lineWidth: Double, colour: Colour): LSeg2ArrDraw =
    diag(cenX, cenY, scale).draw(lineWidth, colour)

  def diagRectangles(diagLength: Double, rectangleWidth: Double, cen: Pt2 = Origin2) = RArr(Rectangle(diagLength, rectangleWidth, 45.degsVec, cen), Rectangle(diagLength, rectangleWidth, -45.degsVec, cen))

  def diagRectangles(diagLength: Double, rectangleWidth: Double, cenX: Double, cenY: Double) = RArr(Rectangle(diagLength, rectangleWidth, 45.degsVec, cenX, cenY), Rectangle(diagLength, rectangleWidth, -45.degsVec, cenX, cenY))
}