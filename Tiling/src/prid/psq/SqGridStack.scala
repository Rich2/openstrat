/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import ostrat.geom.Pt2

class SqGridStack(val bottomCenR: Int, val topCenR: Int, val leftCenC: Int, val rightCenC: Int, val numLevels: Int, val rOffset: Int) extends SqGridMulti
{
 // override def gridMans: Arr[SqGridMan] = ???

  /** The grids of this tile grid system. */
  override val grids: Arr[SqGrid] = iUntilMap(0, numLevels)(l => new SqGrid(rOffset + bottomCenR, rOffset + topCenR, leftCenC, rightCenC))

  override def arrIndex(sc: SqCen): Int = ???
  override def arrIndex(r: Int, c: Int): Int = ???

  /** The top most point in the grid where the value of y is maximum. */
  override def top: Double = ???

  /** The bottom most point in the grid where the value of y is minimum. */
  override def bottom: Double = ???

  /** The left most point in the grid where x is minimum. */
  override def left: Double = ???

  /** The right most point in the grid where the value of x is maximum. */
  override def right: Double = ???

  override def sqCoordToPt2(sqCoord: SqCoord): Pt2 = ???
}

object SqGridStack
{
  def apply(bottomCenR: Int, topCenR: Int, leftCenC: Int, rightCenC: Int, numLevels: Int, rOffset: Int): SqGridStack =
  { val rMin = bottomCenR.roundUpToEven
    val rMax = topCenR.roundDownToEven
    val cMin = leftCenC.roundUpToEven
    val cMax = rightCenC.roundDownToEven
    new SqGridStack(rMin, rMax, cMin, cMax, numLevels: Int, rOffset)
  }
}