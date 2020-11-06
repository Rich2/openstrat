/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is either an alignedElem which inherits from BoundedElem or [[pCanv.CanvasPlatform]] or [[pCanv.Panel]]. */
trait AlignedElem
{
  def xCen: Double
  def yCen: Double
  def cen: Pt2
  def topRight: Pt2
  def topRightDelta: Pt2 = topRight -*- cen
  def bottomRight: Pt2
  def bottomRightDelta: Pt2 = bottomRight -*- cen
  def bottomLeft: Pt2
  def bottomLeftDelta: Pt2 = bottomLeft -*- cen
  def topLeft: Pt2
  def topLeftDelta: Pt2 = topLeft -*- cen
  //def topCen: Vec2
}