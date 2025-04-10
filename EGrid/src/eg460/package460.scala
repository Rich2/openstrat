/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 460km, a C scale of 115km. A hex tile area of 183250.975km².
 *  A maximum Isle size of 130458.946km² excludes Java 128297km², South Island(NZ) 150437km²,=.
 *  [[Isle13]] 111847.519km² => 130458.946km².
 *  [[Isle12]] 94667.740km² => 111847.519km².
 *  [[Isle11]] 78919.609km² => 94667.740km².
 *  [[Isle10]] 64603.127km² => 78919.609km².
 *  [[Isle9]] 51718.292km² => 64603.127km².
 *  [[Isle8]] 40265.106km² => 51718.292km².
 *  [[Isle7]] 30243.569km² => 40265.106km².
 *  [[Isle6]] 21653.679km² => 30243.569km².
 *  [[Isle5]] 14495.438km² => 21653.679km².
 *  [[Isle4]] 8768.845km² => 14495.438km².
 *  [[Isle3]] 4473.900km² => 8768.845km².
 *  Less than 4473.900km². Faroes 1399km² + Shetlands 1467km². */
package object eg460
{
  val fullTerrs: RArr[Long460Terrs] = RArr(Terr460E0, Terr460E30, Terr460E60, Terr460E90, Terr460E120, Terr460E150,Terr460E180, Terr460W150,
    Terr460W120, Terr460W90, Terr460W60, Terr460W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid460LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long460Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}