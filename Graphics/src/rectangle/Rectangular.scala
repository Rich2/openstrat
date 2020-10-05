/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The Rectangular trait includes both Rectangles and objects that have a Rectangular quality. The leaf classes of this class may or may not be
 *  squares and may or may not be aligned to the X and Y Axes. */
trait Rectangular
{ //def width: Double
  //def height: Double
  def xCen: Double
  def yCen: Double
  def cen: Vec2
}