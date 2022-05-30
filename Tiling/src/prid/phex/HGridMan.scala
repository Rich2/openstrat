/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

/** [[HGrid]] manager. */
trait HGridMan
{ /** The grid that this object manages for the [[HGridMulti]]. */
  def grid: HGrid

  /** The position of this grid manager and grid within the grid sequence of the [[HGridMulti]]. */
  def thisInd: Int

  /** The tile data array index start for this grid manager. */
  def indexStart: Int

  def numTiles: Int = grid.numTiles
  final def outSteps(hCen: HCen): HStepCenArr = outSteps(hCen.r, hCen.c)
  def outSteps(r: Int, c: Int): HStepCenArr
  def innerSidesForeach(f: HSide => Unit): Unit
  def outerSidesForeach(f: HSide => Unit): Unit
  def numSides: Int = sidesFold((acc, _) => acc + 1)
  def sidesForeach(f: HSide => Unit): Unit

  def sidesFold[A](init: A)(f: (A, HSide) => A): A =
  { var acc: A = init
    sidesForeach{hs => acc = f(acc, hs) }
    acc
  }

  def sidesFold[A](f: (A, HSide) => A)(implicit ev: DefaultValue[A]): A = sidesFold(ev.default)(f)

  //def sideLines(implicit grider: HGridSys): LineSegArr = sides.map(_.lineSeg)
  def hCenSteps(hCen: HCen): HDirnArr = grid.hCenSteps(hCen) ++ outSteps(hCen).map(_.step)
  def offset: Vec2

  /** Default implementation may need removal. */
  def adjTilesOfTile(tile: HCen): HCenArr = grid.adjTilesOfTile(tile)

  def findStep(startHC: HCen, endHC: HCen): Option[HDirn] =
    if(grid.hCenExists(endHC)) grid.findStep(startHC, endHC) else outSteps(startHC).find(_.endHC == endHC).map(_.step)

  def findStepEnd(startHC: HCen, step: HDirn): Option[HCen] =
  { val r1 = grid.findStepEnd(startHC, step)
    if(r1.nonEmpty) r1 else outSteps(startHC).find(_.step == step).map(_.endHC)
  }
}