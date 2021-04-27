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
    res.unsafeSetElemInts(0, r + 1, c + 1)
    iToForeach(1, num - 1){ i =>
      res.unsafeSetElemInts(i * 2 - 1, r + 1, c + i * 2)
      res.unsafeSetElemInts(i * 2, r + 1, c + i * 2 + 1)
    }
    iToForeach(num - 1, 1, - 1) { i =>
      res.unsafeSetElemInts(num * 3 - i + 1, r - 1, c + i * 2 + 1)
      res.unsafeSetElemInts(num * 3 - i + 2, r - 1, c + i * 2)
    }
    res.unsafeSetElemInts(num * 4 + 1, r - 1, c + 1)
    res.unsafeSetElemInts(num * 4 + 2, r - 1, c)
    res.unsafeSetElemInts(num * 4 + 3, r - 1, c - 1)
    res.unsafeSetElemInts(num * 4 + 4, r + 1, c - 1)
    res.unsafeSetElemInts(num * 4 + 5, r + 1, c)
    res
  }

  /** The polygon of this HCenRow of tiles if it is part of a regular grid. */
  def polygonReg: Polygon = hVertPolygon.toPolygon(_.toPt2)// verts.map(_.toPt2).toPolygon
}
