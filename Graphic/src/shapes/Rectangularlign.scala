/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle aligned to the X and Y axes. */
trait Rectangularlign extends Rectanglular
{ def width: Double
  def height: Double
  def xCen: Double
  def yCen: Double
  @inline final override def xTopLeft: Double = xCen - width / 2
  @inline final override def yTopLeft: Double = yCen + height / 2
  @inline final override def topLeft: Vec2 = Vec2(xTopLeft, yTopLeft)
  @inline final override def xTopRight: Double = xCen + width / 2
  @inline final override def yTopRight: Double = yCen + height / 2
  @inline final override def topRight: Vec2 = Vec2(xTopRight, yTopRight)
  @inline final override def xBottomRight: Double = xCen + width / 2
  @inline final override def yBottomRight: Double = yCen - height / 2
  @inline final override def bottomRight: Vec2 = Vec2(xBottomRight, yBottomRight)
  @inline final override def xBottomLeft: Double = xCen - width / 2
  @inline final override def yBottomLeft: Double = yCen - height / 2
  @inline final override def bottomLeft: Vec2 = Vec2(xBottomLeft, yBottomLeft)
}