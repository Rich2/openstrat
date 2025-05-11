/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom.*, pglobe.*, prid.phex.*

/** A hex grid system consisting of multiple non-polar [[EGrid]]s. */
trait EGridLongMulti extends EGridSys, EGridMulti
{ override type ManT = EGridLongMan
  override def grids: RArr[EGridLong]

  override val gridMans: RArr[EGridLongMan]

  /** The spacing between the centres of the grid on the X axis. */
  def gridsXSpacing: Double

  /** The longitude Int for the head grid. */
  def headGridInt: Int

  /** The Delta in c from Grid to Grid. */
  final def hcDelta: Int = 1024

  override def hCoordLL(hc: HCoord): LatLong = manMapex(hc)(_.grid.hCoordLL(hc))
  def top: Double = grids(0).top
  def bottom: Double = grids(0).bottom
  def left: Double = grids(0).left
  def right: Double = grids.last.right
  override def manGetex(r: Int, c: Int): EGridLongMan = gridMans((c / hcDelta - headGridInt) %% 12)

  def manFind(r: Int, c: Int): Option[EGridLongMan] =
  { val i = (c  / hcDelta - headGridInt) %% 12
    ife(i < 0 | i >= gridMans.length, None, Some(gridMans(i)))
  }

  override def sepTileLtOpt(hSide: HSep): Option[HCen] = manFind(hSide).flatMap(_.sideTileLtFind(hSide))

  override def sepTileRtOpt(hSide: HSep): Option[HCen] = manFind(hSide).flatMap(_.sideTileRtFind(hSide))
}