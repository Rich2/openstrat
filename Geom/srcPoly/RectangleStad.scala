/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A stadium / pill / discorectangle / obround shape. */
trait RectangleStad extends ShapeCentred
{ type ThisT <: Rectangle
  
  override def rotate(angle: AngleVec): RectangleStad = ???
  
}
