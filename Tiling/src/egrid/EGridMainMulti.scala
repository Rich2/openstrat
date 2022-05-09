/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

trait EGridMainSys extends EGridSys

trait EGridMainMulti extends EGridMainSys with EGridMulti
{ override val gridMans: Arr[EGridMainMan]
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))
  def top: Double = grids(0).top
  def bottom: Double = grids(0).bottom
  def left: Double = grids(0).left
  def right: Double = grids.last.right
}