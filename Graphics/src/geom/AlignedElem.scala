/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is either an alignedElem which inherits from BoundedElem or [[pCanv.CanvasPlatform]] or [[pCanv.Panel]]. */
trait AlignedElem
{
  def xCen: Double
  def yCen: Double
  def cen: Vec2
  def topRight: Vec2
  def topRightDelta: Vec2 = topRight - cen
  def bottomRight: Vec2
  def bottomRightDelta: Vec2 = bottomRight - cen
  def bottomLeft: Vec2
  def bottomLeftDelta: Vec2 = bottomLeft - cen
  def topLeft: Vec2
  def topLeftDelta: Vec2 = topLeft - cen
  //def topCen: Vec2
}