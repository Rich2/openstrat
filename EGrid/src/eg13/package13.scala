/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1300km, a C scale of 375km. An area of 49883.063km. A minimum Island area of 8313.844km, which includes
 * Crete but excludes Zealand and Mallorca.  */
package object eg13
{
  val fullTerrs: RArr[Long13Terrs] = RArr(Terr13E0, Terr13E30, Terr13E60, Terr13E90, Terr13E120, null, null, null, null, null, null,
    //, Terr13E150,Terr13E180,
    //Terr13W150, Terr13W120, Terr13W90, Terr13W60
    Terr13W30)


  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid13LongMulti): HCenLayer[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid13LongMulti): HSideOptLayer[WSide, WSideSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid13LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine
}