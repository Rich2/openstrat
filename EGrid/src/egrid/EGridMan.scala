/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, prid._, phex._

trait EGridMan
{ /** The multi grid system that contains the grid that this is managing. */
  def sys: EGridMulti

  def grid: EGrid

  /** The position of this grid manager and grid within the grid sequence of the [[HGridMulti]]. */
  def thisInd: Int

  /** The tile data array index start for this grid manager. */
  def indexStart: Int

  def hCenExists(r: Int, c: Int): Boolean

  final def hCenExists(hCen: HCen): Boolean = hCenExists(hCen.r, hCen.c)
  def sideTile1AndVertUnsafe(hSide: HSide): (HCen, Int)
  /** Gives the index into an [[HSide]] data layer's backing [[Array]]. */
  def sideArrIndex(r: Int, c: Int): Int

  /** Foreach's over the sides of the [[HGrid]] that are outer sides, ie nt links of the [[HGridSys]]. This will be a subset of the [[Hgrids]]'s
   * outer sides, as some of the girds outer sides will link to hexes in other grids within the [HGridSys]] grid system. */
  def outerSidesForeach(f: HSide => Unit): Unit

  def sidesForeach(f: HSide => Unit): Unit

  /** The offet is used in the implementation of the flatHCoordToPt2(hCoord: HCoord) method in [[HGridMulti]] where it is added to the [[Pt2]] value
   * given by the [[HGrid]]. */
  def offset: Vec2
  /** The number of tiles in the Hex grid this manager class is managing. */
  def numTiles: Int = grid.numTiles

  def adjTilesOfTile(tile: HCen): HCenArr

  /** Tile steps from a hex within this managers grid to other grids. */
  final def outSteps(hCen: HCen): HStepCenArr = outSteps(hCen.r, hCen.c)

  /** Tile steps from a hex within this managers grid to other grids. */
  def outSteps(r: Int, c: Int): HStepCenArr

  def findStep(startHC: HCen, endHC: HCen): Option[HStep] =
    if (grid.hCenExists(endHC)) grid.findStep(startHC, endHC) else outSteps(startHC).find(_.endHC == endHC).map(_.step)

  def findStepEnd(startHC: HCen, step: HStep): Option[HCen] = {
    val r1 = grid.findStepEnd(startHC, step)
    if (r1.nonEmpty) r1 else outSteps(startHC).find(_.step == step).map(_.endHC)
  }
  def hCenSteps(hCen: HCen): HStepArr = grid.hCenSteps(hCen) ++ outSteps(hCen).map(_.step)

  def innerRowInnerSidesForeach(r: Int)(f: HSide => Unit): Unit

  /** Foreach's over sides of the [[HGrid]] that are links ar inner sides within the [[HGridSys]]. Note this wil include all the links of the
   * [[HGrid]] plus outer sides of the [[HGrid]] that link to hexs in other [[HGrid]]s within the system. */
  final def linksForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowInnerSidesForeach(r)(f))

  lazy val sideIndexStart: Int =
    ife(thisInd == 0, 0, sys.gridMans(thisInd - 1).sideIndexStart + sys.gridMans(thisInd - 1).numSides)

  /** This method should only be used when you know both side tiles exist. */
  def unsafeSideTiles(hSide: HSide): (HCen, HCen) = (sideTile1Unsafe(hSide), sideTile2Unsafe(hSide))

  /** This method should only be used when you know the side tile exists. */
  def sideTile1Unsafe(hSide: HSide): HCen

  /** This method should only be used when you know the side tile exists. */
  def sideTile2Unsafe(hSide: HSide): HCen

  def sidesFold[A](init: A)(f: (A, HSide) => A): A =
  { var acc: A = init
    sidesForeach { hs => acc = f(acc, hs) }
    acc
  }

  def sidesFold[A](f: (A, HSide) => A)(implicit ev: DefaultValue[A]): A = sidesFold(ev.default)(f)
  def numSides: Int = sidesFold((acc, _) => acc + 1)
}