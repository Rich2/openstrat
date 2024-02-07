/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A Line segment where the vertices of specified in [[HvOffset]]s. */
class LineSegHvOffset(val int1: Int, val int2: Int, val int3: Int, val int4: Int, val int5: Int, val int6: Int) extends
  LineSegLikeInt6[HvOffset]
{ override def startPt: HvOffset = new HvOffset(int1, int2, int3)
  override def endPt: HvOffset = new HvOffset(int4, int5, int6)
}

object LineSegHvOffset
{ def apply(v1: HvOffset, v2: HvOffset): LineSegHvOffset = new LineSegHvOffset(v1.int1, v1.int2, v1.int3, v2.int1, v2.int2, v2.int3)
}