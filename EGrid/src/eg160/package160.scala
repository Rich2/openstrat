/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 160km, a C scale of 40km. A tile area of 22170.250km.
 * km² => km²
 * km² => km²
 * km² => km²
 * km² => km²
 * 1060.881km² => km²
 * 541.265km² =>1060.881km² South Uist 320.3km² North Uist 303km² Benbcuala 82.03km² Berneray 10.1km² Grimsay 8.33km² = 723.76km² */
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