/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._

/** Scenario for 3 Grid system for 0E, 30E and 60E. */
object Scen13S0E2 extends EScenLongMulti
{ override val title: String = "1300km 0E - 30E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(3, 0, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 13km grid system for 30W 0E, 30E and 60E. */
object Scen13S11E2 extends EScenLongMulti
{ override val title: String = "1300km 30W - 60E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 11, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 2 Grid system from 60E to 90E. */
object Scen13S2E3 extends EScenLongMulti
{ override val title: String = "1300km 60E - 90E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 2, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen13All extends EScenLongMulti
{ override val title: String = "1300km all longitude terrain only scenario."
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(12, 0, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 3 13km grid system for 150W, 120W, 90W and 60W */
object Scen13Americas extends EScenLongMulti
{ override val title: String = "1300km 150W - 60W"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(5, 7, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}