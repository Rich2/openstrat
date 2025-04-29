/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A trait for classes of line paths specified by [[[HCoord]] hex grid tile coordinates. */
final class LinePathHC(val arrayUnsafe: Array[Int]) extends AnyVal with HCoordSeqSpec with LinePathInt2[HCoord]
{ override type ThisT = LinePathHC
  override type PolygonT = PolygonHC
  override type LineSegT = LSegHC
  override type LineSegArrT = LineSegHCArr
  override def typeStr: String = "LinePathHC"
  override def fromArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
  override def polygonFromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)

  def lineSegArr: LineSegHCArr = {
    val newArray = new Array[Int]((arrayLen - 2).max0 * 2)
    var j = 0
    iUntilForeach(2, arrayLen, 2){ i =>
      newArray(j) = arrayUnsafe(i - 2)
      newArray(j + 1) = arrayUnsafe(i - 1)
      newArray(j + 2) = arrayUnsafe(i)
      newArray(j + 3) = arrayUnsafe(i + 1)
      j = j + 4
    }
    new LineSegHCArr(newArray)
  }
}

object LinePathHC extends CompanionSlInt2[HCoord, LinePathHC]
{ override def fromArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
}