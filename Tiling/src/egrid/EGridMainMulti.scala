/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

trait EGridMainSys extends EGridSys

trait EGridMainMulti extends EGridMainSys with EGridMulti
{
  override def grids: Arr[EGridMain]

  override val gridMans: Arr[EGridMainMan]
  def cGridDelta: Double

  /** The longitude Int for the head grid. */
  def headGridInt: Int

  /** The Delta in c form Grid to Grid. */
  final def hcDelta: Int = 1024

  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))
  def top: Double = grids(0).top
  def bottom: Double = grids(0).bottom
  def left: Double = grids(0).left
  def right: Double = grids.last.right
  override def unsafeGetMan(r: Int, c: Int): EGridMan = gridMans((c  / hcDelta - headGridInt) %% 12)
}