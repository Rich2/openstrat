/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 320km, a C scale of 80km. A hex tile area of 88681.001km.
 *  A minimum Island area of 1/6 8313.844km, which88681.001
 *  [[Tile13]] 54126.587km² => 63133.251km².
 *  [[Tile12]] 45812.743km² => 54126.587km².
 *  [[Tile11]] 38191.720km² => 45812.743km².
 *  [[Tile10]] 31263.517km² => 38191.720km².
 *  [[Tile9]] 25028.134km² => 31263.517km².
 *  [[Tile8]] 19485.571km² => 25028.134km².
 *  [[Tile7]] 14635.829km² => 19485.571km².
 *  [[Tile6]] 10478.907km² => 14635.829km².
 *  [[Tile5]] 7014.805km² => 10478.907km².
 *  [[Tile4]] 4243.524km² => 7014.805km².
 *  [[Tile3]] 2165.063km² => 4243.524km².
 *  includes Crete but excludes Zealand and Mallorca.  */
package object eg320
{
  val fullTerrs: RArr[Long320Terrs] = RArr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150, Terr320E180, Terr320W150,
    Terr320W120, Terr320W90, Terr320W60, Terr320W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid320LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid320LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid320LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid320LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long320Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}