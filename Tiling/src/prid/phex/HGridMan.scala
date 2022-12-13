/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

/** [[HGrid]] manager. The motivation for this class is that a tile grid has no knowledge of the multi tile grid system within which it has been
 *  placed. The idea is to encapsulate the code for managing a grid within an object rather than having it all within the [[HGridSys]] Not sure if its
 *  a good idea or not. */
trait HGridMan
{ /** The multi grid system that contains the grid that this is managing. */
  def sys: HGridMulti

  /** The grid that this object manages for the [[HGridMulti]]. */
  def grid: HGrid

  /** The position of this grid manager and grid within the grid sequence of the [[HGridMulti]]. */
  def thisInd: Int

  /** The tile data array index start for this grid manager. */
  def indexStart: Int

  /** The number of tiles in the Hex grid this manager class is managing. */
  def numTiles: Int = grid.numTiles

  /** Tile steps from a hex within this managers grid to other grids. */
  final def outSteps(hCen: HCen): HStepCenArr = outSteps(hCen.r, hCen.c)

  /** Tile steps from a hex within this managers grid to other grids. */
  def outSteps(r: Int, c: Int): HStepCenArr

  def sideArrIndex(r: Int, c : Int): Int = grid.sideArrIndex(r, c)

  lazy val sideIndexStart: Int =
    ife(thisInd == 0, 0, sys.gridMans(thisInd - 1).sideIndexStart + sys.gridMans(thisInd - 1).numSides)

  /** Foreach's over sides of the [[HGrid]] that are links ar inner sides within the [[HGridSys]]. Note this wil include all the links of the
   * [[HGrid]] plus outer sides of the [[HGrid]] that link to hexs in other [[HGrid]]s within the system. */
  def linksForeach(f: HSide => Unit): Unit

  /** Foreach's over the sides of the [[HGrid]] that are outer sides, ie nt links of the [[HGridSys]]. This will be a subset of the [[Hgrids]]'s
   * outer sides, as some of the girds outer sides will link to hexes in other grids within the [HGridSys]] grid system. */
  def outerSidesForeach(f: HSide => Unit): Unit

  def numSides: Int = sidesFold((acc, _) => acc + 1)
  def sidesForeach(f: HSide => Unit): Unit

  def sidesFold[A](init: A)(f: (A, HSide) => A): A =
  { var acc: A = init
    sidesForeach{hs => acc = f(acc, hs) }
    acc
  }

  def sidesFold[A](f: (A, HSide) => A)(implicit ev: DefaultValue[A]): A = sidesFold(ev.default)(f)

  def hCenSteps(hCen: HCen): HDirnArr = grid.hCenSteps(hCen) ++ outSteps(hCen).map(_.step)

  /** The offet is used in the implementation of the flatHCoordToPt2(hCoord: HCoord) method in [[HGridMulti]] where it is added to the [[Pt2]] value
   * given by the [[HGrid]]. */
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