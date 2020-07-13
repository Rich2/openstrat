/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A rectangle class that has position and may not be aligned to the X and Y axes. */
final case class Rect(xCen: Double, yCen: Double, x0: Double, y0: Double, x1: Double, y1: Double) extends Rectangle with AffinePreserve
{ override type ThisT = Rect
  override def width: Double = (cenRight - cen).magnitude * 2
  override def height: Double = (topCen - cen).magnitude * 2
  override def xTopRight: Double = x0
  override def yTopRight: Double = y0
  override def topRight: Vec2 = Vec2(x0, y0)
  override def xBottomRight: Double = x1
  override def yBottomRight: Double = y1
  override def bottomRight: Vec2 = Vec2(x1, y1)
  override def topLeft: Vec2 = 2 * cen - bottomRight
  override def xTopLeft: Double = topLeft.x
  override def yTopLeft: Double = topLeft.y
  override def bottomLeft: Vec2 = 2 * cen - topRight
  override def xBottomLeft: Double = bottomLeft.x
  override def yBottomLeft: Double = bottomLeft.y
  def cenRight: Vec2 = (topRight + bottomRight) / 2
  def topCen: Vec2 = (topLeft + topRight) / 2

  override def fTrans(f: Vec2 => Vec2): Rect = Rect.points(f(cen), f(topRight), f(bottomRight))
}


object Rect
{ def points(cen: Vec2, topRight: Vec2, bottomRight: Vec2): Rect = new Rect(cen.x, cen.y, topRight.x, topRight.y, bottomRight.x, bottomRight.y)
}