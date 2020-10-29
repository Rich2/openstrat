/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Rectangle that is in part specified by points v3Cen and v1Cen. This is the Square and Rect classes. This trait is purely for implementation. It
 *  does not have value as a library user interface type. */
trait RectS2S4 extends Rectangle
{
  final override def cen: Vec2 = s3Cen mid s1Cen
  final override def xCen: Double = cen.x
  final override def yCen: Double = cen.y
  final override def width1: Double = (s2Cen - s4Cen).magnitude
  final def rotationRadians: Double = rotation.radians
  @inline final override def rotation: Angle = (s2Cen - s4Cen).angle
  final override def v1: Vec2 = s2Cen + Vec2(0, width2 / 2).rotate(rotation)
  final override def x1: Double = v1.x
  final override def y1: Double = v1.y
  final override def v2: Vec2 = s2Cen + Vec2(0, -width2 / 2).rotate(rotation)
  final override def x2: Double = v2.x
  final override def y2: Double = v2.y
  @inline final def v3: Vec2 = s4Cen + Vec2(0, -width2 / 2).rotate(rotation)
  @inline final def x3: Double = v3.x
  @inline final def y3: Double = v3.y
  @inline final def v4: Vec2 = s4Cen + Vec2(0, width2 / 2).rotate(rotation)
  @inline final def x4: Double = v4.x
  @inline final def y4: Double = v4.y

  final override def s1Cen: Vec2 = v4 mid v1
  final override def s3Cen: Vec2 = v2 mid v3
  def xS2Cen: Double
  def yS2Cen: Double
  final override  def s2Cen: Vec2 = Vec2(xS2Cen, yS2Cen)
  def xS4Cen: Double
  def yS4Cen: Double
  final override def s4Cen: Vec2 = Vec2(xS4Cen, yS4Cen)
}
