/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

/** [[HGrid]] manager. */
trait HGridMan
{ val grid: HGrid
  val arrIndex: Int
  def numTiles: Int = grid.numTiles
  final def outSteps(hCen: HCen): HStepCenArr = outSteps(hCen.r, hCen.c)
  def outSteps(r: Int, c: Int): HStepCenArr
  def sides: HSideArr = grid.sides
  def numSides: Int = grid.sides.length
  def sideLines(implicit grider: HGridSysFlat): LineSegs = sides.map(_.lineSeg)
  def hCenSteps(hCen: HCen): HDirnArr = grid.hCenSteps(hCen) ++ outSteps(hCen).map(_.step)

  /** Default implementation may need removal. */
  def adjTilesOfTile(tile: HCen): HCenArr = grid.adjTilesOfTile(tile)

  def findStep(startHC: HCen, endHC: HCen): Option[HDirn] =
    if(grid.hCenExists(endHC)) grid.findStep(startHC, endHC) else outSteps(startHC).find(_.endHC == endHC).map(_.step)

  def findStepEnd(startHC: HCen, step: HDirn): Option[HCen] =
  { val r1 = grid.findStepEnd(startHC, step)
    if(r1.nonEmpty) r1 else outSteps(startHC).find(_.step == step).map(_.endHC)
  }
}