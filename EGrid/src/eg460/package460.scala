/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._, egrid._

/** Hex tile grids for Earth with a hex scale of 460km, a C scale of 115km. A hex tile area of 183250.975km². A maximum Isle size of 120974.276km²
 *  which excludes Sulawesi, NZ south island and Java down to 57981.753km², which includes NZ North Island, Luzon, Newfoundland, Cuba, Iceland,
 *  Mindanao, Ireland, Hokkaidō, Hispaniola, Sakhalin, Banks Island, Sri Lanka and Tasmania.
 *  Isle8 down to 35075.382 km² which includes Devon Island down to Kyūshū
 *  Isle6 down 21653.679km² which includes Hawaii combined.
 *  Isle5 down to 14495.438km²
 *  Isle4 down to 8768.845km²
 *  Isle3 down to 4473.900km²
 *  Isle2 down to 1610.604km²
 *  Isle1 down to 357.9124km²
 *  */
package object eg460
{
  val fullTerrs: RArr[Long460Terrs] = RArr(Terr460E0, Terr460E30, Terr460E60, Terr460E90, Terr460E120, Terr460E150,Terr460E180, Terr460W150,
    Terr460W120, Terr460W90, Terr460W60, Terr460W30)

  def fullTerrsHCenLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHcRefSys[WTile] = iToMap(0, subSys.numGrids - 1) { i =>
    val ft = fullTerrs((i + subSys.headGridInt) %% 12)
    ft.terrs.spawn(ft.grid, subSys.grids(i))
  }.combine

  def fullTerrsSideLayerSpawn(implicit subSys: EGrid460LongMulti): LayerHSOptSys[WSide, WSideSome] =
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