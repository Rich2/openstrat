/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1300km, a C scale of 375km. A hex tile area of 1.463582932 million km².
 *  A maximum Isle area of 1041945.271km², excludes Greenland
 *  [[Isle13]] 893300.129km² => 1041945.271km².
 *  [[Isle12]] 756089.229km² => 893300.129km². New Guinea.
 *  [[Isle11]] 630312.571km² => 756089.229km² Borneo.
 *  [[Isle10]] 515970.154km² => 630312.571km². Madagascar.
 *  [[Isle9]] 413061.979km² => 515970.154km². Baffin Island, Sumatra.
 *  [[Isle8]] 321588.046km² => 413061.979km².
 *  [[Isle7]] 241548.355km² => 321588.046km². Philippines combined 300000km², New Zealand combined 268021km².
 *  [[Isle6]] 172942.905km² => 243930.488km². Honshu, Victoria Island, Great Britain, Ellesmere Island.
 *  [[Isle5]] 115771.696km² => 172942.905km². Sulawesi, South Island(NZ), Java.
 *  [[Isle4]] 70034.730km² => 115771.696km². Iceland 103.592km2.
 *  [[Isle3]] 35732.005km² => 70034.730km². */
package object eg13
{
  val fullTerrs: RArr[Long13Terrs] = RArr(Terr13E0, Terr13E30, Terr13E60, Terr13E90, Terr13E120, Terr13E150, Terr13E180, Terr13W150, Terr13W120,
    Terr13W90, Terr13W60, Terr13W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid13LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long13Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSepLayerSpawn(implicit subSys: EGrid13LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long13Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid13LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long13Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid13LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long13Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}