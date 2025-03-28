/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A 4 sided [[Polygon]]. */
trait Quadrilateral extends Polygon4Plus
{ type ThisT <: Quadrilateral
  final override def numVerts: Int = 4
}