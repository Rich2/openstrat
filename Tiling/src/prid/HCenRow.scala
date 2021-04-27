/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A row or a segment a row of Hex tiles in a grid. The start / left centre HexGrid coordinate and the number of tiles in the row.. */
class HCenRow(val r: Int, val c: Int, val num: Int)
{
  def verts: HVerts =
  {
    val res = HVerts.uninitialised(num * 4 + 2)
    res
  }

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: HVertPolygon =
  {
    val res = HVertPolygon.uninitialised(num * 4 + 2)
    res
  }

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.map(_.toPt2).toPolygon
}
