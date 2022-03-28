/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

case class HGridMan(grid: SqGrid, arrIndex: Int)
{ def numTiles: Int = grid.numTiles
  //final def outSteps(hCen: SqCen): Arr[(HStep, HCen)] = outSteps(hCen.r, hCen.c)
  //def outSteps(r: Int, c: Int): Arr[(HStep, HCen)] = Arr()
  def sides: Arr[SqSide] = ??? // grid.sides
  //val numSides: Int = grid.sides.length
  //def sideLines(implicit grider: HGriderFlat): LineSegs = sides.map(_.lineSeg)
}

trait SqGridMulti extends SqGrider {

}

/** A simple development class of 2 square grids. 1 directly above the ohter. */
case class SqStory2(minCenR: Int, maxCenR: Int, minCenC: Int, maxCenC: Int) extends SqGridMulti
{
  /** The total number of tile centres in this tile Grid system. */
  override def numTiles: Int = ???
}