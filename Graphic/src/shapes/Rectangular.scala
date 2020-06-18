/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The Rectangular trait includes both Rectangles and objects that have a Rectangular quality. The leaf classes of this class may or may not be
 *  squares and may or may not be aligned to the X and Y Axes. */
trait Rectanglular
{ def width: Double
  def height: Double
  def xCen: Double
  def yCen: Double
  @inline final def cen: Vec2 = xCen vv yCen
  def xTopLeft: Double
  def yTopLeft: Double
  def topLeft: Vec2
  def xTopRight: Double
  def yTopRight: Double
  def topRight: Vec2
  def xBottomRight: Double
  def yBottomRight: Double
  def bottomRight: Vec2
  def xBottomLeft: Double
  def yBottomLeft: Double
  def bottomLeft: Vec2
}