/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom.pglobe._, prid._, phex._

trait EGridMainSys extends EGridSys
{

}

trait EGridMainMan extends EGridMan
{ override val grid: EGridMain
}

trait EGridMainMulti extends EGridMainSys with EGridMulti
{ override val gridMans: Arr[EGridMainMan]
  override val grids: Arr[EGridMain] = gridMans.map(_.grid)

  override def hCoordLL(hc: HCoord): LatLong = ???//grid
}