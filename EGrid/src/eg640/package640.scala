/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 640km, a C scale of 160km. A hex tile area of 354724.005km².
 * Island maximum 234173.269km²
 *  [[Isle10]] 125054.068km² => 234173.269km². Honshu, Victoria Island, Great Britain, Ellesmere Island, Sulawesi, South Island(NZ), Java.
 *  [[Isle9]] 100112.536km² => 125054.068km².
 *  [[Isle8]] 77942.286km² => 100112.536km².
 *  [[Isle7]] 58543.317km² => 77942.286km².
 *  [[Isle6]] 41915.629km² => 58543.317km².
 *  [[Isle5]] 28059.223km² => 41915.629km².
 *  [[Isle4]] 16974.097km² => 28059.223km².
 *  [[Isle3]] 8660.254km² => 16974.097km². */
package object eg640
{
  val fullTerrs: RArr[Long640Terrs] = RArr(Terr640E0, Terr640E30, Terr640E60, Terr640E90, Terr640E120, Terr640E150,Terr640E180, Terr640W150,
    Terr640W120, Terr640W90, Terr640W60, Terr640W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid640LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid640LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid640LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid640LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long640Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}