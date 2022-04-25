/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._

trait EGridMainMan extends HGridMan
{
  override val grid: EGridMain
}

trait EGridMainMulti extends HGridMulti
{ override val gridMans: Arr[EGridMainMan]
  override val grids: Arr[EGridMain] = gridMans.map(_.grid)
}