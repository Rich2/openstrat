/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pEarth._, prid.phex._

/** Hex tile grids for Earth with a hex scale of 220km, a C scale of 80km. */
package object eg220
{
  val fullTerrs: RArr[Long220Terrs] = RArr(Terr220E0, Terr220E30, null, null, null, null, null,
    null, null, null, null, null)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid220LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).hCenLayerSpawn(ft.grid, ft.terrs)
  }.combine

  def fullTerrsSideOptLayerSpawn(implicit subSys: EGrid220LongMulti): HSideOptLayer[WSide] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sideOptsFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid220LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).cornerLayerSpawn(ft.grid, ft.corners)
  }.combine
}