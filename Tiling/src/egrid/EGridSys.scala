/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom.pglobe._, prid._, phex._

trait EGridSys extends HGridSys
{ def cScale: Length
  def hCoordLL(hc: HCoord): LatLong
}

trait EGridMan extends HGridMan
{ override val grid: EGrid
}

trait EGridMulti extends EGridSys with HGridMulti
{ override type GridT = EGrid
  override type ManT = EGridMan
  override val grids: Arr[EGrid] = gridMans.map(_.grid)
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))
}