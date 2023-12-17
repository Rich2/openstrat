/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0E and 30E */
object Scen460S0E1 extends EScenLongMulti
{ override val title: String = "460km 0E - 30E"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(2, 0, 116)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 460km grid system for 90E, 120E and 150E */
object Scen460ChinaJapan extends EScenLongMulti
{ override val title: String = "460km 90E - 150E"
  implicit override val gridSys: EGrid460LongMulti = EGrid460.multi(3, 3, 118, 120)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 460km grid system for 30W 0E, 30E and 60E. */
object Scen460E0E2 extends EScenLongMulti
{ override val title: String = "460km 30W - 60E"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(3, 0, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}
//
///** Scenario for 8 Grid system from 60W to 150E. */
//object Scen460S10E5 extends EScenLongMulti
//{ override val title: String = "460km 60W - 150E"
//  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(8, 10, 98)
//  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** Just terrain scenario for all longitudes grid system. */
//object Scen460All extends EScenLongMulti
//{ override val title: String = "460km all longitude terrain only scenario."
//  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(12, 0, 96)
//  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** Terrain only scenario for North America. 3 460km grid system for 150W, 120W, 90W and 60W */
//object Scen460Americas extends EScenLongMulti
//{ override val title: String = "460km 150W - 60W"
//  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(5, 7, 96)
//  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** 2 460km grid system for Northern Canada 120W and 90W. */
//object Grids460NCanada extends EGrid460LongMulti
//{ override val grids: RArr[EGridLongFull] = EGrid460.grids(2, 8, 124)
//  override def headGridInt: Int = 8
//  override def gridsXSpacing: Double = 40
//  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
//}
//
///** Scenario for 2 460km grid system for Northern Canada 120W and 90W. */
//object Scen460NCanada extends EScenLongMulti
//{ override val title: String = "460km Far North Canada"
//  implicit override val gridSys: EGrid460LongMulti = Grids460NCanada
//  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}