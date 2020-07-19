/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A rectangle class that has position and may not be aligned to the X and Y axes. */
final case class Rect(x0: Double, y0: Double, x1: Double, y1: Double, width: Double) extends Rectangle with AffinePreserve
{ override type ThisT = Rect
  //override def width: Double = ??? // (cenRight - cen).magnitude * 2
  override def height: Double = ??? //(topCen - cen).magnitude * 2
  override def xCen: Double = ???
  override def yCen: Double = ???
  override def cen: Vec2 = xCen vv yCen
  override def v0: Vec2 = x0 vv y0
  override def v1: Vec2 = x1 vv y1

  def v2: Vec2 = ???
  def v3: Vec2 = ???
  def x2: Double = ???
  def x3: Double = ???
  def y2: Double = ???
  def y3: Double = ???
  override def fTrans(f: Vec2 => Vec2): Rect = Rect.points(f(cen), f(v0), f(v1))

  override def rotation: Angle = (v0 - v3).angle
}


object Rect
{ def points(cen: Vec2, topRight: Vec2, bottomRight: Vec2): Rect = ??? // new Rect(cen.x, cen.y, topRight.x, topRight.y, bottomRight.x, bottomRight.y)
}