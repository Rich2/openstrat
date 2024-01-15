/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0°E and 30°E */
object Scen320s0e1 extends EScenLongMulti
{ override val title: String = "320km 0°E - 30°E"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(2, 0, 120)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 320km grid system for 90°E, 120°E and 150°E */
object Scen320ChinaJapan extends EScenLongMulti
{ override val title: String = "320km China-Japan 90°E - 150°E"
  implicit override val gridSys: EGrid320LongMulti = EGrid320.multi(3, 3, 124)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 4 320km grid system for 150°W, 120°W, 90°W and 60°W */
object Scen320NorthAmerica extends EScenLongMulti
{ override val title: String = "320km North America 150°W - 60°W"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(4, 7, 124)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 320km grid system for 30°W 0°E, 30°E and 60°E. */
object Scen320S11E2 extends EScenLongMulti
{ override val title: String = "320km 30°W - 60°E"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(4, 11, 118)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen320All extends EScenLongMulti
{ override val title: String = "320km all longitude terrain only scenario."
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(12, 0, 124)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for South America 3 Grid system from 90°W to 30°W. */
object Scen320SouthAmerica extends EScenLongMulti
{ override val title: String = "320km South America 90°W - 30°W"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(3, 9, 124)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}