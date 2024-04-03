/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 120km, a C scale of 40km. Hex tile area 12470.765km..
 * [[Isle13]] km² => km².
 * [[Isle12]] km² => km²².
 * [[Isle11]] km² => km².
 * [[Isle10]] km² => km².
 * [[Isle9]] km² => km².
 * [[Isle8]] km² => km².
 * [[Isle7]] km² => km².
 * [[Isle6]] 1473.596km² => km².
 * [[Isle5]] 986.457km² => 1473.596km².
 * [[Isle4]] 596.745km² => 986.457km².
 * [[Isle3]] 304.462km² => 596.745km². */
package object eg120
{
  val fullTerrs: RArr[Long120Terrs] = RArr(Terr120E0, Terr120E30, Terr120E60, null, null, null, null, null, null, null, null, Terr120W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid120LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid120LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid120LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullNamesHCenLayerSpawn(implicit subSys: EGrid120LongMulti): LayerHcRefSys[String] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft: Long120Terrs = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.hexNames.spawn(ft.grid, subSys.grids(i))
  }.combine
}