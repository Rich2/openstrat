/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Rectangle aligned to the X and Y axes. */
trait Rectlign extends Rect with Rectangularlign
{ @inline final override def x0: Double = xTopRight
  @inline final override def y0: Double = yTopRight
  @inline final override def v0: Vec2 = x0 vv y0
  @inline final override def x1: Double = xTopLeft
  @inline final override def y1: Double = yTopLeft
  @inline final override def v1: Vec2 = x1 vv y1
  @inline final override def cen: Vec2 = xCen vv yCen
  override def rotation: Angle = 0.degs
  @inline final def x2: Double = xBottomLeft
  @inline final def y2: Double = yBottomLeft
  @inline final def v2: Vec2 = bottomLeft
  @inline final def x3: Double = xTopLeft
  @inline final def y3: Double = yTopLeft
  @inline final def v3: Vec2 = topLeft
}