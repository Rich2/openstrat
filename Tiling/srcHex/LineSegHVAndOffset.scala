/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A Line segment where the vertices of specified in [[HvRelOffset]]s. */
class LineSegHVAndOffset(val int1: Int, val int2: Int, val int3: Int, val int4: Int, val int5: Int, val int6: Int) extends
  LineSegLikeInt6[HvRelOffset]
{ override def startPt: HvRelOffset = new HvRelOffset(int1, int2, int3)
  override def endPt: HvRelOffset = new HvRelOffset(int4, int5, int6)
}

object LineSegHVAndOffset
{ def apply(v1: HvRelOffset, v2: HvRelOffset): LineSegHVAndOffset = new LineSegHVAndOffset(v1.int1, v1.int2, v1.int3, v2.int1, v2.int2, v2.int3)
}