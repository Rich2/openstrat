/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0E and 30E */
object Scen460S0E1 extends EScenLongMulti
{ override val title: String = "460km 0E - 30E"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(2, 0, 114)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 460km grid system for 90°E, 120°E and 150°E */
object Scen460ChinaJapan extends EScenLongMulti
{ override val title: String = "460km 90°E - 150°E"
  implicit override val gridSys: EGrid460LongMulti = EGrid460.multi(3, 3, 114, 134)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 4 460km grid system for 150°W, 120°W, 90°W and 60°W */
object Scen460NorthAmerica extends EScenLongMulti
{ override val title: String = "460km North America 150°W - 60°W"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(4, 7, 112, 134)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 460km grid system for 30°W, 0°E, 30°E and 60°E. */
object Scen460S11E2 extends EScenLongMulti
{ override val title: String = "460km 30°W - 60°E"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(4, 11, 114)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen460All extends EScenLongMulti
{ override val title: String = "460km all longitude terrain only scenario."
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(12, 0, 114, 134)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for South America. 3 460km grid system for 90°W, 60°W and 30°W */
object Scen460SouthAmerica extends EScenLongMulti
{ override val title: String = "460km South America 90°W - 30°W"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(3, 9, 112, 134)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 460km grids across the Dateline for 150°E, 180°E, and 150°W. */
object Scen460DateLine extends EScenLongMulti
{ override val title: String = "460km Date Line 150°E - 120°W"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(3, 5, 114, 140)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}