/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class PolygonTuple[A] (unsafeArray: Array[Double], val val1: A)
{
  def polygon: Polygon = new PolygonGen(unsafeArray)
}
