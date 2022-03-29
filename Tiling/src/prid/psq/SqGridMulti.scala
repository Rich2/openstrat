/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

case class SqGridMan(grid: SqGrid, arrIndex: Int)
{ def numTiles: Int = grid.numTiles
  //final def outSteps(hCen: SqCen): Arr[(HStep, HCen)] = outSteps(hCen.r, hCen.c)
  //def outSteps(r: Int, c: Int): Arr[(HStep, HCen)] = Arr()
  def sides: Arr[SqSide] = ??? // grid.sides
  //val numSides: Int = grid.sides.length
  //def sideLines(implicit grider: HGriderFlat): LineSegs = sides.map(_.lineSeg)
}

trait SqGridMulti extends SqGrider {
  def gridMans: Arr[SqGridMan]
  def grids: Arr[SqGrid] = gridMans.map(_.grid)
}

trait SqGridMultiFlat extends SqGridMulti

/** A simple development class of 2 square grids. Probably not a useful class for an application but a stage on the evolution of more exotic classes. */
final class SqGrids2(val minCenR: Int, val maxCenR: Int, val minC1: Int, val maxC1: Int, val minC2: Int, maxC2: Int) extends SqGridMultiFlat
{

  override def gridMans: Arr[SqGridMan] = Arr()

  /** The total number of tile centres in this tile Grid system. */
  override def numTiles: Int = ???
}