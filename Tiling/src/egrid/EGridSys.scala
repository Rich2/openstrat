/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom.pglobe._, prid._, phex._

trait EGridSys extends HGridSys
{
  def hCoordLL(hc: HCoord): LatLong
}

trait EGridMan extends HGridMan
{ override val grid: EGrid
}

trait EGridMulti extends EGridSys with HGridMulti
{ override val gridMans: Arr[EGridMan]
  override val grids: Arr[EGrid] = gridMans.map(_.grid)

  override def hCoordLL(hc: HCoord): LatLong = ???//grid
}