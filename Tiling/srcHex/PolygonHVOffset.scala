/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A Line segment where the vertices of specified in [[HVOffset]]s. */
class LineSegHVAndOffset(val int1: Int, val int2: Int, val int3: Int, val int4: Int, val int5: Int, val int6: Int) extends
  LineSegLikeInt6[HVOffset]
{ override def startPt: HVOffset = new HVOffset(int1, int2, int3)
  override def endPt: HVOffset = new HVOffset(int4, int5, int6)
}

object LineSegHVAndOffset
{ def apply(v1: HVOffset, v2: HVOffset): LineSegHVAndOffset = new LineSegHVAndOffset(v1.int1, v1.int2, v1.int3, v2.int1, v2.int2, v2.int3)
}

/** A polygon where the vertices are specified in [[HVOffset]]s. */
class PolygonHVOffset(val unsafeArray: Array[Int]) extends HVOffsetSeqLike with PolygonLikeInt3[HVOffset]
{ override type ThisT = PolygonHVOffset
  override type SideT = LineSegHVAndOffset
  override def typeStr: String = "HVAndOffsetPolygon"
  override def fromArray(array: Array[Int]): PolygonHVOffset = new PolygonHVOffset(array)
  override def verts: HVOffsetArr = new HVOffsetArr(unsafeArray)

  override def sides: Arr[LineSegHVAndOffset] = ???

  @inline def side(index: Int): LineSegHVAndOffset = LineSegHVAndOffset(vert(index), ife(index == numVerts - 1, vert(0), vert(index + 1)))

  override def sidesForeach[U](f: LineSegHVAndOffset => U): Unit =
  { var i = 0
    while (i < numVerts) {
      f(side(i)); i += 1
    }
  }

  def toPolygon(f: HCoord => Pt2)(implicit sys: HGridSys): Polygon = map(_.toPt2(f))
  def project(proj: HSysProjection): Polygon = map{ _.toPt2(proj.transCoord(_))(proj.parent) }
}

object PolygonHVOffset extends CompanionSeqLikeInt3[HVOffset, PolygonHVOffset]
{
  override def fromArray(array: Array[Int]): PolygonHVOffset = new PolygonHVOffset(array)
}

trait PolgonHVAndOffsetCommonBuilder extends BuilderSeqLikeInt3[PolygonHVOffset]
{ override type BuffT = HVOffsetBuff
  override def fromIntArray(array: Array[Int]): PolygonHVOffset = new PolygonHVOffset(array)
  override def fromIntBuffer(inp: ArrayBuffer[Int]): HVOffsetBuff = new HVOffsetBuff(inp)
}

class PolygonHVAndOffsetMapBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3MapBuilder[HVOffset, PolygonHVOffset]

class PolygonHVAndOffsetFlatBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3FlatBuilder[HVOffset, PolygonHVOffset]