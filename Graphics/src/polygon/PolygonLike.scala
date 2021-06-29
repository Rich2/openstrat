/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLike[VertT] extends Any
{
  def foreachVert[U](f: VertT => U): Unit
}
