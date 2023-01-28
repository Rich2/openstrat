/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
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


  /** Gives the index into an [[HSide]] data layer's backing [[Array]]. */
  def sideArrIndex(r: Int, c : Int): Int


  /** Foreach's over sides of the [[HGrid]] that are links ar inner sides within the [[HGridSys]]. Note this wil include all the links of the
   * [[HGrid]] plus outer sides of the [[HGrid]] that link to hexs in other [[HGrid]]s within the system. */
  def linksForeach(f: HSide => Unit): Unit

  /** Foreach's over the sides of the [[HGrid]] that are outer sides, ie nt links of the [[HGridSys]]. This will be a subset of the [[Hgrids]]'s
   * outer sides, as some of the girds outer sides will link to hexes in other grids within the [HGridSys]] grid system. */
  def outerSidesForeach(f: HSide => Unit): Unit

  def sidesForeach(f: HSide => Unit): Unit

  /** The offet is used in the implementation of the flatHCoordToPt2(hCoord: HCoord) method in [[HGridMulti]] where it is added to the [[Pt2]] value
   * given by the [[HGrid]]. */
  def offset: Vec2

  /** Default implementation may need removal. */
  def adjTilesOfTile(tile: HCen): HCenArr = grid.adjTilesOfTile(tile)


}