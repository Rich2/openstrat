/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

trait HCenRowLike
{
  def r: Int
  def c: Int
  def num: Int
  def verts: HVerts = new HVerts(setHVertArray)

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: PolygonHC = new PolygonHC(setHVertArray)

  /** Creates the backing Array[Int] of [[HVert]]s for this HCenRow. This same array can be used inside an [[HVerts]] or a [[PolygonHC]] class. */
  def setHVertArray: Array[Int] =
  { val res = new Array[Int]((num * 4 + 2) * 2)
    res.set2Elems(0, r + 1, c + 2)
    iToForeach(2, num ){ i =>
      res.set2Elems(i * 2 - 3, r + 1, c + i * 4 - 4)
      res.set2Elems(i * 2 - 2, r + 1, c + i * 4 - 2)
    }
    iToForeach(num, 2, - 1) { i =>
      res.set2Elems(num * 4 - i * 2 - 1, r - 1, c + i * 4 - 2)
      res.set2Elems(num * 4 - i * 2,     r - 1, c + i * 4 - 4)
    }
    res.set2Elems(num * 4 - 3, r - 1, c + 2)
    res.set2Elems(num * 4 - 2, r - 1, c)
    res.set2Elems(num * 4 - 1, r - 1, c - 2)
    res.set2Elems(num * 4,     r + 1, c - 2)
    res.set2Elems(num * 4 + 1, r + 1, c)
    res
  }

  /** The polygon of this HCenRow of tiles if it is part of a regular grid. */
  def polygonReg: Polygon = hVertPolygon.toPolygon(_.toPt2Reg)
}

/** A row or a segment a row of Hex tiles in a grid. The start / left centre HexGrid coordinate and the number of tiles in the row.. */
case class HCenRow(r: Int, c: Int, num: Int) extends HCenRowLike

case class HCenRowValue[A](r: Int, c: Int, num: Int, value: A) extends HCenRowLike