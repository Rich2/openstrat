/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Rectangle like final class aligned to the X and Y axes. */
trait Rectangularlign extends OrdinalEdgePoints
{ def width: Double
  def height: Double
  def left: Double = cenX - width / 2
  def right: Double = cenX + width / 2
  def top: Double = cenY + height / 2
  def bottom: Double = cenY - height / 2
  def xTopLeft: Double = cenX - width / 2
  def yTopLeft: Double = cenY + height / 2
  def topLeft: Pt2 = Pt2(xTopLeft, yTopLeft)
  def xTopRight: Double = cenX + width / 2
  def yTopRight: Double = cenY + height / 2
  def topRight: Pt2 = Pt2(xTopRight, yTopRight)
  def xBottomRight: Double = cenX + width / 2
  def yBottomRight: Double = cenY - height / 2
  def bottomRight: Pt2 = Pt2(xBottomRight, yBottomRight)
  def xBottomLeft: Double = cenX - width / 2
  def yBottomLeft: Double = cenY - height / 2
  def bottomLeft: Pt2 = Pt2(xBottomLeft, yBottomLeft)
}