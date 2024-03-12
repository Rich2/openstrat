/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 220km, a C scale of 55km. A tile area of 41915.629km².
 * [[Tile13]] 25583.269km² => 29840.326km².
 * [[Tile12]] 25583.269km² => 25583.269km².
 * [[Tile11]] 18051.555km² => 25583.269km².
 * [[Ilse10]] 14776.896km² => 18051.555km².
 * [[Isle9]] 11829.704km² => 14776.896km².
 * [[Isle8]] 9209.977km² => 11829.704km².
 * [[Isle7]] 6917.716km² => 9209.977km².
 * [[Isle6]] 4952.921km² => 6917.716km².
 * [[Isle5]] 3315.591km² => 4952.921km².
 * [[Isle4]] 2005.728km² => 3315.591km².
 * [[Isle3]] 1023.330km² => 2005.728km². */
package object eg220
{
  val fullTerrs: RArr[Long220Terrs] = RArr(Terr220E0, Terr220E30, Terr220E60, null, null, null, null, null, Terr220W120, Terr220W90, Terr220W60, Terr220W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid220LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid220LongMulti): LayerHSOptSys[WSep, WSepSome] =
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

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid220LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long220Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}