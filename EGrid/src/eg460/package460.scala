/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 460km, a C scale of 115km. A hex tile area of 183250.975km².
 *  A maximum Isle size of 120974.276km² excludes South Island(NZ), Java
 *  Isle10 57981.753km² => 120974.276km².
 *  Isle8 35075.382km² => 57981.753km².
 *  Isle6 35075.382km² => 21653.679km².
 *  Isle5 14495.438km² => 21653.679km².
 *  Isle4 8768.845km² => 14495.438km².
 *  Isle3 4473.900km² => 8768.845km². */
package object eg460
{
  val fullTerrs: RArr[Long460Terrs] = RArr(Terr460E0, Terr460E30, Terr460E60, Terr460E90, Terr460E120, Terr460E150,Terr460E180, Terr460W150,
    Terr460W120, Terr460W90, Terr460W60, Terr460W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHSOptSys[WSep, WSepSome] =
  { val arr = iToMap(0, subSys.numGrids - 1) { i =>
      val ft = fullTerrs((i + subSys.headGridInt) %% 12)
      (ft.grid, ft.sTerrs)
    }
    subSys.sidesOptFromPairsSpawn(arr)
  }

  def fullTerrsCornerLayerSpawn(implicit subSys: EGrid460LongMulti): HCornerLayer = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.corners.spawn(ft.grid, subSys.grids(i))
  }.combine
}