/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 1300km, a C scale of 375km. A hex tile area of 1.463582932 million km².
 *  A maximum Isle area of 966193.420km², which excludes Greenland
 *  Isle 966193.420km² <= 463086.787km². Includes New Guinea, Borneo, Madagascar, Baffin Island.
 *  Isle8 463086.787km² <= 243930.488km², Sumatra, Philippines combined, New Zealand combined.
 *  Isle6 243930.488km² <= 172942.905km². Honshu, Victoria Island, Great Britain, Ellesmere Island.
 *  Isle5 172942.905km² <= 115771.696km². Sulawesi, South Island(NZ), Java.  */
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