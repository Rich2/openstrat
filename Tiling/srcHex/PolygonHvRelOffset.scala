/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A polygon where the vertices are specified in [[HvRelOffset]]s. */
class PolygonHvRelOffset(val arrayUnsafe: Array[Int]) extends HvRelOffsetSeqLike with PolygonLikeInt3[HvRelOffset]
{ override type ThisT = PolygonHvRelOffset
  override type SideT = LineSegHVAndOffset
  override def typeStr: String = "HVAndOffsetPolygon"
  override def fromArray(array: Array[Int]): PolygonHvRelOffset = new PolygonHvRelOffset(array)
  override def verts: HvRelOffsetArr = new HvRelOffsetArr(arrayUnsafe)

  override def sides: Arr[LineSegHVAndOffset] = ???//new LineSegHCArr(arrayForSides)

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

object PolygonHvRelOffset extends CompanionSeqLikeInt3[HvRelOffset, PolygonHvRelOffset]
{
  override def fromArray(array: Array[Int]): PolygonHvRelOffset = new PolygonHvRelOffset(array)
}

trait PolgonHVAndOffsetCommonBuilder extends BuilderSeqLikeInt3[PolygonHvRelOffset]
{ override type BuffT = HVOffsetBuff
  override def fromIntArray(array: Array[Int]): PolygonHvRelOffset = new PolygonHvRelOffset(array)
  override def fromIntBuffer(inp: ArrayBuffer[Int]): HVOffsetBuff = new HVOffsetBuff(inp)
}

class PolygonHVAndOffsetMapBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3MapBuilder[HvRelOffset, PolygonHvRelOffset]

class PolygonHVAndOffsetFlatBuilder extends PolgonHVAndOffsetCommonBuilder with PolygonInt3FlatBuilder[HvRelOffset, PolygonHvRelOffset]