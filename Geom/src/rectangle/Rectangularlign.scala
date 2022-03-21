/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Rectangle like final class aligned to the X and Y axes. */
trait Rectangularlign extends OrdinalEdgePoints
{ def unsafeArray: Array[Double]
  def width: Double
  def height: Double
  @inline final def xTopLeft: Double = cenX - width / 2
  @inline final def yTopLeft: Double = cenY + height / 2
  @inline final def topLeft: Pt2 = Pt2(xTopLeft, yTopLeft)
  @inline final def xTopRight: Double = cenX + width / 2
  @inline final def yTopRight: Double = cenY + height / 2
  @inline final def topRight: Pt2 = Pt2(xTopRight, yTopRight)
  @inline final def xBottomRight: Double = cenX + width / 2
  @inline final def yBottomRight: Double = cenY - height / 2
  @inline final def bottomRight: Pt2 = Pt2(xBottomRight, yBottomRight)
  @inline final def xBottomLeft: Double = cenX - width / 2
  @inline final def yBottomLeft: Double = cenY - height / 2
  @inline final def bottomLeft: Pt2 = Pt2(xBottomLeft, yBottomLeft)
}