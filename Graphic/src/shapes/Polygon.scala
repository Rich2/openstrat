/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Polygon extends Any with TranserAll
{ def length: Int
  def apply(index: Int): Vec2
}
