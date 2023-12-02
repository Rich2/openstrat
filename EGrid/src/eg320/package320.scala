/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 320km, a C scale of 80km. An area of 49883.063km. A minimum Island area of 1/6 8313.844km, which
 *  includes Crete but excludes Zealand and Mallorca.  */
package object eg320
{
  val fullTerrs: RArr[Long320Terrs] = RArr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150,Terr320E180,
    Terr320W150, Terr320W120, Terr320W90, Terr320W60, Terr320W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid320LongMulti): LayerHcSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid320LongMulti): HSideOptLayer[WSide, WSideSome] =
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
}