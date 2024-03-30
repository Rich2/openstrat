/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 160km, a C scale of 40km. A tile area of 22170.250km.
 * [[Isle13]] 13531.646km² => km².
 * [[Isle12]] 11453.185km² => 13531.646km².
 * [[Isle11]] 9547.930km² => 11453.185km².
 * [[Isle10]] 7815.879km² => 9547.930km².
 * [[Isle9]] 6257.033km² => 7815.879km².
 * [[Isle8]] 4871.392km² => 6257.033km².
 * [[Isle7]] 3658.957km² => 4871.392km².
 * [[Isle6]] 2619.726km² => 3658.957km².
 * [[Isle5]] 1753.701km² => 2619.726km².
 * [[Isle4]] 1060.881km² => 1753.701km².
 * [[Isle3]] 541.265km² => 1060.881km². */
package object eg160
{
  val fullTerrs: RArr[Long160Terrs] = RArr(Terr160E0, Terr160E30, null, null, Terr160E120, Terr160E150, null, null, Terr160W120, Terr160W90, Terr160W60, Terr160W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid160LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid160LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid160LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid160LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long160Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}