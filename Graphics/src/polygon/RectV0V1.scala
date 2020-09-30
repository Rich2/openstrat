/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle that is in part specified by points v0 and v1. This is the Square and Rect classes. This trait is purely for implementation. It does not
 * have value as a library user interface type. */
trait RectCenV0 extends Rectangle
{ final override def cen: Vec2 = Vec2(xCen, yCen)
  final override def width: Double = (v0Mid1 - cen).magnitude * 2
  final override def v0: Vec2 = x0 vv y0
  @inline final def v2: Vec2 = 2 * cen - v0
  @inline final def x2: Double = v2.x
  @inline final def y2: Double = v2.y
  @inline final def v3: Vec2 = 2 * cen - v1
  @inline final def x3: Double = v3.x
  @inline final def y3: Double = v3.y


}
