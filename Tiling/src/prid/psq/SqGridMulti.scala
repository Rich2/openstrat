/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import ostrat.geom.{LineSegArr, Pt2}

/** Square grid manager to be used n cojunction with the [[SqGridMulti]] trait. */
case class SqGridMan(grid: SqGrid, arrIndex: Int)
{ def numTiles: Int = grid.numTiles
  //final def outSteps(hCen: SqCen): Arr[(HStep, HCen)] = outSteps(hCen.r, hCen.c)
  //def outSteps(r: Int, c: Int): Arr[(HStep, HCen)] = Arr()
  def sides: RArr[SqSide] = ??? // grid.sides
  //val numSides: Int = grid.sides.length
  //def sideLines(implicit grider: HGriderFlat): LineSegs = sides.map(_.lineSeg)
}

/** A system of [[SqGrid]]s. */
trait SqGridMulti extends SqGridSys with TGridMulti
{ final type GridT = SqGrid
  //def gridMans: Arr[SqGridMan]
  //final lazy val grids: Arr[SqGridAlt] = gridMans.map(_.grid)
  override def defaultView(pxScale: Double = 50): SqGridView = grids(0).defaultView(pxScale)

  override def foreach(f: SqCen => Unit): Unit = grids.foreach(_.foreach(f))
}

/** A system of [[SqGrid]]s in a flat 2D plane. Trait has no metods at the moment, unlike the corresponding [[HGridMulti]]. */
//trait SqGridMultiFlat extends SqGridMulti with SqGridSysFlat

/** A simple development class of 2 square grids. Probably not a useful class for an application but a stage on the evolution of more exotic classes. */
final class SqGridsAdjPair(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends SqGridMulti
{
  val grid1 = SqGrid(minCenR, maxCenR, minC1, maxC1)
  val grid2 = SqGrid(minCenR, maxCenR, minC2, maxC2)
  //override def gridMans: Arr[SqGridMan] = Arr()

  override def arrIndex(sc: SqCen): Int = ???
  override def arrIndex(r: Int, c: Int): Int = ???

  /** The grids of this tile grid system. */
  override def grids: RArr[SqGrid] = RArr(grid1, grid2)

  //override val numTiles: Int = grid1.numTiles + grid2.numTiles
  override def top: Double = maxCenR + 1
  override def bottom: Double = minCenR - 1

  /** The left most point in the grid where x is minimum. */
  override def left: Double = ???

  /** The right most point in the grid where the value of x is maximum. */
  override def right: Double = ???

  override def flatSqCoordToPt2(sqCoord: SqCoord): Pt2 = ???

  override def sqCenExists(r: Int, c: Int): Boolean = ???

  override def sideLines: LineSegArr = ???
}