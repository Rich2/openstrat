/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1300km, a C scale of 375km. A hex tile area of 1.463582932 million km² A minimum Island area of
 *  243930.488km², which includes Borneo, Madagascar, Baffin Island, Sumatra, Honshu, but not Victoria Island, Great Britain, Ellesmere Island or
 *  Sulawesi.  */
package object eg13
{
  val fullTerrs: RArr[Long13Terrs] = RArr(Terr13E0, Terr13E30, Terr13E60, Terr13E90, Terr13E120, Terr13E150, Terr13E180, Terr13W150, Terr13W120,
    Terr13W90, Terr13W60, Terr13W30)


  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid13LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long13Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid13LongMulti): LayerHSOptSys[WSide, WSideSome] =
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
}