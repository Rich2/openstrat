/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._

/** 160km scenario for 2 Grid system for 0°E and 30°E */
object Scen160S0E1 extends EScenLongMulti
{ override val title: String = "160km °0E - 30°E"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(2, 0, 262)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 160km scenario for 2 Grid system for 120°E and 150°E */
object Scen160ChinaJapan extends EScenLongMulti
{ override val title: String = "160km 120°E - 150°E"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(2, 4, 252, 272)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 160km scenario for 3 Grid system for 30°W, 0°E and 30°E */
object Scen160S11E2 extends EScenLongMulti
{ override val title: String = "160km 30°W - 60°E"
  override implicit val gridSys: EGrid160LongMulti = EGrid160.multi(3, 11, 280)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}