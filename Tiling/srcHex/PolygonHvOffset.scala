/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom.*, collection.mutable.ArrayBuffer

/** A polygon where the vertices are specified in [[HvOffset]]s. */
class PolygonHvOffset(val arrayUnsafe: Array[Int]) extends HvOffsetSeqLike, PolygonInt3[HvOffset]
{ override type ThisT = PolygonHvOffset
  override type SideT = LSegHvOffset
  override def typeStr: String = "HVAndOffsetPolygon"
  override def fromArray(array: Array[Int]): PolygonHvOffset = new PolygonHvOffset(array)
  override def verts: HvOffsetArr = new HvOffsetArr(arrayUnsafe)

  override def sides: Arr[LSegHvOffset] = new LineSegHvOffsetArr(arrayForSides)

  @inline def side(index: Int): LSegHvOffset = LSegHvOffset(vert(index), ife(index == numVerts - 1, vert(0), vert(index + 1)))

  override def sidesForeach[U](f: LSegHvOffset => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }

  def toPolygon(f: HCoord => Pt2)(implicit sys: HGridSys): Polygon = map(_.toPt2(f))
  def project(proj: HSysProjection): Polygon = map{ _.toPt2(proj.transCoord(_))(proj.parent) }
}

object PolygonHvOffset extends CompanionSlInt3[HvOffset, PolygonHvOffset]
{
  override def fromArray(array: Array[Int]): PolygonHvOffset = new PolygonHvOffset(array)
}

trait PolgonHVAndOffsetCommonBuilder extends BuilderSeqLikeInt3[PolygonHvOffset]
{ override type BuffT = HvOffsetBuff
  override def fromIntArray(array: Array[Int]): PolygonHvOffset = new PolygonHvOffset(array)
  override def fromIntBuffer(inp: ArrayBuffer[Int]): HvOffsetBuff = new HvOffsetBuff(inp)
}

class PolygonHVAndOffsetMapBuilder extends PolgonHVAndOffsetCommonBuilder, PolygonInt3BuilderMap[HvOffset, PolygonHvOffset]

class PolygonHVAndOffsetFlatBuilder extends PolgonHVAndOffsetCommonBuilder, PolygonInt3FlatBuilder[HvOffset, PolygonHvOffset]