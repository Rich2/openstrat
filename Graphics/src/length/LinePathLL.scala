/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class LinePathLL(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike
{ override type ThisT = LinePathLL
  override def unsafeFromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
  override def typeStr: String = "LinePathLL"
}