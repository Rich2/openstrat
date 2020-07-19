/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait RectV0V1 extends Rectangle
{ final override def cen: Vec2 = sline0.midPtToRight(width / 2)
  final override def xCen: Double = cen.x
  final override def yCen: Double = cen.y
  final override def v0: Vec2 = x0 vv y0
  final override def v1: Vec2 = x1 vv y1
  @inline final def v2: Vec2 = sline0.endToRight(width)
  @inline final def x2: Double = v2.x
  @inline final def y2: Double = v2.y
  @inline final def v3: Vec2 = sline0.startToRight(width)
  @inline final def x3: Double = v3.x
  @inline final def y3: Double = v3.y
}
