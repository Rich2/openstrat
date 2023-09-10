/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1 megametre, a C scale of 250km. An area of 866025.403 km². A minimum Island area of 144337.567km²,
 *  which includes Honshu, Victoria Island, Great Britain, Ellesmere Island, Sulawesi and the New Zealand Islands combined, but not individually and
 *  not Java, North Island, Luzon or Cuba.  */
package object egmega
{
  val fullTerrs: RArr[LongMegaTerrs] = RArr(TerrMegaE0, TerrMegaE30, TerrMegaE60, TerrMegaE90, TerrMegaE120, TerrMegaE150,TerrMegaE180, TerrMegaW150,
    TerrMegaW120, TerrMegaW90, TerrMegaW60, TerrMegaW30)

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