/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 120km, a C scale of 40km. A tile area of 7014.805km. A minimum island size of 1169.134km. */
package object eg120
{
  val fullTerrs: RArr[Long120Terrs] = RArr(Terr120E0, Terr120E30, Terr120E60, null, null, null, null, null, null, null, null, null)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid120LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).hCenLayerSpawn(ft.grid, ft.terrs)
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid120LongMulti): HSideLayer[WSide] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesFromPairsSpawn(arr, WSideNone)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid120LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).cornerLayerSpawn(ft.grid, ft.corners)
  }.combine
}