/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle that is in part specified by points v0 and v1. This is the Square and Rect classes. This trait is purely for implementation. It does not
 * have value as a library user interface type. */
trait RectCenV0 extends Rectangle
{ final override def cen: Vec2 = Vec2(xCen, yCen)
  final override def width1: Double = (s1Cen - cen).magnitude * 2
  override def width2: Double = (v1 - v2).magnitude
  final override def v1: Vec2 = x1 vv y1
  @inline final def v3: Vec2 = 2 * cen - v1
  @inline final def x3: Double = v3.x
  @inline final def y3: Double = v3.y
  @inline final def v4: Vec2 = 2 * cen - v2
  @inline final def x4: Double = v4.x
  @inline final def y4: Double = v4.y
  final override def ls3Cen: Vec2 = v4.mid(v1)
}
