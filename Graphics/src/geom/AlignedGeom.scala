/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is either an alignedElem which inherits from Geom or something like a Panel. */
trait AlignedElem
{
  def xCen: Double
  def yCen: Double
  def cen: Vec2
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
  /*def xTopCen: Double
  def yTopCen: Double
  def topCen: Vec2*/
}

trait AlignedGeom extends BoundedElem with AlignedElem
