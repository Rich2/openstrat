/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import ostrat.geom._

class SqGridStack(val bottomCenR: Int, val topCenR: Int, val leftCenC: Int, val rightCenC: Int, val numLevels: Int, val rOffset: Int) extends SqGridMulti
{
 // override def gridMans: Arr[SqGridMan] = ???

  /** Finds step from Start [[SqCen]] to target from [[SqCen]]. */
  override def stepFind(startHC: SqCen, endHC: SqCen): Option[SqStep] = ???

  /** The grids of this tile grid system. */
  override val grids: RArr[SqGrid] = iUntilMap(0, numLevels)(l => new SqGrid(rOffset + bottomCenR, rOffset + topCenR, leftCenC, rightCenC))

  override def layerArrayIndex(sc: SqCen): Int = ???
  override def layerArrayIndex(r: Int, c: Int): Int = ???

  /** The top most point in the grid where the value of y is maximum. */
  override def top: Double = ???

  /** The bottom most point in the grid where the value of y is minimum. */
  override def bottom: Double = ???

  /** The left most point in the grid where x is minimum. */
  override def left: Double = ???

  /** The right most point in the grid where the value of x is maximum. */
  override def right: Double = ???

  override def flatSqCoordToPt2(sqCoord: SqCoord): Pt2 = ???

  override def sqCenExists(r: Int, c: Int): Boolean = ???

  override def sideLines: LineSegArr = ???
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