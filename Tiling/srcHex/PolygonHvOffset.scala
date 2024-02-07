/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A polygon where the vertices are specified in [[HvOffset]]s. */
class PolygonHvOffset(val arrayUnsafe: Array[Int]) extends HvOffsetSeqLike with PolygonLikeInt3[HvOffset]
{ override type ThisT = PolygonHvOffset
  override type SideT = LineSegHvOffset
  override def typeStr: String = "HVAndOffsetPolygon"
  override def fromArray(array: Array[Int]): PolygonHvOffset = new PolygonHvOffset(array)
  override def verts: HvOffsetArr = new HvOffsetArr(arrayUnsafe)

  override def sides: Arr[LineSegHvOffset] = new LineSegHvOffsetArr(arrayForSides)

  @inline def side(index: Int): LineSegHvOffset = LineSegHvOffset(vert(index), ife(index == numVerts - 1, vert(0), vert(index + 1)))

  override def sidesForeach[U](f: LineSegHvOffset => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  def toPolygon(f: HCoord => Pt2)(implicit sys: HGridSys): Polygon = map(_.toPt2(f))
  def project(proj: HSysProjection): Polygon = map{ _.toPt2(proj.transCoord(_))(proj.parent) }
}

object PolygonHvOffset extends CompanionSeqLikeInt3[HvOffset, PolygonHvOffset]
{
  override def fromArray(array: Array[Int]): PolygonHvOffset = new PolygonHvOffset(array)
}

trait PolgonHVAndOffsetCommonBuilder extends BuilderSeqLikeInt3[PolygonHvOffset]
{ override type BuffT = HvOffsetBuff
  override def fromIntArray(array: Array[Int]): PolygonHvOffset = new PolygonHvOffset(array)
  override def fromIntBuffer(inp: ArrayBuffer[Int]): HvOffsetBuff = new HvOffsetBuff(inp)
}

class PolygonHVAndOffsetMapBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3MapBuilder[HvOffset, PolygonHvOffset]

class PolygonHVAndOffsetFlatBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3FlatBuilder[HvOffset, PolygonHvOffset]