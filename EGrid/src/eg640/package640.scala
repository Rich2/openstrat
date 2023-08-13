/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 640km, a C scale of 80km. An area of 49883.063km. A minimum Island area of 8313.844km, which includes
 * Crete but excludes Zealand and Mallorca.  */
package object eg640
{
  val fullTerrs: RArr[Long640Terrs] = RArr(Terr640E0)//, Terr640E30, Terr640E60, Terr640E90, Terr640E120, Terr640E150,Terr640E180,
    //Terr640W150, Terr640W120, Terr640W90, Terr640W60, Terr640W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid640LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid640LongMulti): HSideOptLayer[WSide, WSideSome] =
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
}