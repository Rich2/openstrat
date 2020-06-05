/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Polygon extends TransElem
{ def length: Int
  def apply(index: Int): Vec2
  def foreach[U](f: Vec2 => U): Unit
  def x0: Double
  def y0: Double
}
