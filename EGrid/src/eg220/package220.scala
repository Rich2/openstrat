/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 220km, a C scale of 80km. A tile area of 41915.629km².
 * A maximum Isle area of 27670.864km²
 * Isle 13262.367km² <= 27670.864km²
 * Isle8 8022.913km² <= 13262.367km²
 * Isle6 4952.921km² <= 8022.913km²
 * Isle5 3315.591km² <= 4952.921km²
 * Isle4 2005.728km² <= 3315.591km²
 * Isle3 1023.330km² <= 2005.728km²
 * A minimum island size of 1/6 5715.767km². */
package object eg220
{
  val fullTerrs: RArr[Long220Terrs] = RArr(Terr220E0, Terr220E30, null, null, null, null, null,
    null, Terr220W120, Terr220W90, Terr220W60, Terr220W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid220LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid220LongMulti): LayerHSOptSys[WSide, WSideSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid220LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine
}