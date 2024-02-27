/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1 megametre, a C scale of 250km. A hex tile area of 866025.403 km².
 * Maximum Isle area 372966.018km² which excludes New Guinea, Borneo, Madagascar, Baffin Island, Sumatra,
 * [[Isle10]] 305307.783km² => 372966.018km².
 * [[Isle9]] 244415.372km² => 305307.783km².
 * [[Isle8]] 190288.785km² => 244415.372km². Includes New Zealand combined 268,021 km², Honshu, Victoria Island, Great Britain, Ellesmere Island.
 * [[Isle7]] 142928.020km² => 190288.785km². Sulawesi, South Island(NZ)
 * [[Isle6]] 102333.079km² => 142928.020km². Java, North Island(NZ), Luzon or Cuba.
 * [[Isle5]] 68503.962km² => 102333.079km².
 * [[Isle4]] 41440.668km² => 68503.962km².
 * [[Isle3]] 21143.198km² => 41440.668km². */
package object egmega
{
  val fullTerrs: RArr[LongMegaTerrs] = RArr(TerrMegaE0, TerrMegaE30, TerrMegaE60, TerrMegaE90, TerrMegaE120, TerrMegaE150,TerrMegaE180, TerrMegaW150,
    TerrMegaW120, TerrMegaW90, TerrMegaW60, TerrMegaW30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGridMegaLongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGridMegaLongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGridMegaLongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGridMegaLongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: LongMegaTerrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}