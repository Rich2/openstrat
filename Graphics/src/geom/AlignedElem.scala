/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait AlignedElem extends BoundedElem
{
  def xTopRight: Double
  def yTopRight: Double
  def topRight: Vec2
  def xBottomRight: Double
  def yBottomRight: Double
  def bottomRight: Vec2
  def xBottomLeft: Double
  def yBottomLeft: Double
  def bottomLeft: Vec2
  def xTopLeft: Double
  def yTopLeft: Double
  def topLeft: Vec2
}