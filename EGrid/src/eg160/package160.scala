/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pEarth._, prid.phex._

/** Hex tile grids for Earth with a hex scale of 100km, a C scale of 40km. */
package object eg160
{
  val fullTerrs: RArr[Long160Terrs] = RArr(Terr160E0, Terr160E30, null, null, Terr160E120, Terr160E150, null, null, null, null, Terr160W60, Terr160W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid160LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).hCenLayerSpawn(ft.grid, ft.terrs)
  }.combine

  def fullTerrsSideOptLayerSpawn(implicit subSys: EGrid160LongMulti): HSideOptLayer[WSide] = {
    val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sideOptsFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid160LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    subSys.grids(i).cornerLayerSpawn(ft.grid, ft.corners)
  }.combine
}