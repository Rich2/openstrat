/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1000km, a C scale of 80km. An area of 49883.063km. A minimum Island area of 8313.844km, which includes
 * Crete but excludes Zealand and Mallorca.  */
package object egmega
{
  val fullTerrs: RArr[LongMegaTerrs] = RArr(TerrMegaE0, TerrMegaE30, TerrMegaE60, TerrMegaE90, TerrMegaE120, TerrMegaE150,TerrMegaE180,
    null, null, null, null,
    //TerrMegaW150, TerrMegaW120, TerrMegaW90, TerrMegaW60
    TerrMegaW30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGridMegaLongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGridMegaLongMulti): HSideOptLayer[WSide, WSideSome] =
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
}