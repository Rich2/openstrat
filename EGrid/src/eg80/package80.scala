/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 80km, a C scale of 20km. A tile area of 5542.562km².
 * A maximum Isle area of 3658.957km²
 * Isle 1753.701km² <= 3658.957km²
 * Isle8 1060.881km² <= 1753.701km²
 * Isle6 654.931km² <= 1060.881km²
 * Isle5 438.425km² <= 654.931km²
 * Isle4 265.220km² <= 438.425km²
 * Isle3 135.316km² <= 265.220km² */
package object eg80
{
  val fullTerrs: RArr[Long80Terrs] = RArr(Terr80E0, Terr80E30, null, null, null, null, null, null, null, null, null, null)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid80LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid80LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid80LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine
}