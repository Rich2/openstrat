/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._

/** Scenario for 3 Grid system for 0°E and 30°E and 60E. */
object ScenMegaS0E1 extends EScenLongMulti
{ override val title: String = "1Mm 0E - 30E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(2, 0, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for China-Japan. 3 grid system from 90°E to 150°E. */
object ScenMegaChinaJapan extends EScenLongMulti
{ override val title: String = "1Mm China-Japan 90°E - 150°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(3, 3, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 4 Mm grid system for 150°W, 120°W, 90°W and 60°W */
object ScenMegaNorthAmerica extends EScenLongMulti
{ override val title: String = "1Mm North America 150°W - 60°W"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 7, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 Mega metre grid system for 30°W 0°E, 3°0E and 60°E. */
object ScenMegaS11E2 extends EScenLongMulti
{ override val title: String = "1Mm 4 grids 30°W - 60°E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 11, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object ScenMegaAll extends EScenLongMulti
{ override val title: String = "1Mm all longitude terrain only scenario."
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(12, 0, 82)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}