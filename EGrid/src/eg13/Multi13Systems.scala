/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._

/** 1300km terrain only scenario for Europe. 2 Grid system for 0°E, and 30°E. */
object Scen13Europe extends EScenLongMulti
{ override val title: String = "1300km Europe 0°E - 30°E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(2, 0, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** Scenario for 3 Grid system from 90°E to 150°E. */
object Scen13ChinaJapan extends EScenLongMulti
{ override val title: String = "1300km China-Japan 60°E - 90°E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(3, 3, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** Terrain only scenario for North America. 4 1300km grid system for ChinaJapan 150°W, 120°W, 90°W and 60°W */
object Scen13NorthAmerica extends EScenLongMulti
{ override val title: String = "1300km North America 150°W - 60°W"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 7, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 1300km terrain only scenario for Africa, Middle East and Europe. 4 grid system for 30W 0°E, 30°E and 60°E. */
object Scen13Africa extends EScenLongMulti
{ override val title: String = "1300km Africa 30°W - 60°E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 11, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 1300km terrain only scenario for India. 2 Grid system for 60°E, and 90°E. */
object Scen13India extends EScenLongMulti
{ override val title: String = "1300km India 60°E - 90°E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(2, 2, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** Terrain only scenario for the Atlantic. 4 1300km grid system for 90°W, 60°W, 30°W and 0°E */
object Scen13Atlantic extends EScenLongMulti
{ override val title: String = "1300km Atlantic 90°W - 0°E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 9, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 1300km terrain only scenario for the Date Line. 3 grid system for 150°E, 180°E, and 150°W */
object Scen13DateLine extends EScenLongMulti
{ override val title: String = "1300km Date Line 150°E - 150°W"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(3, 5, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 1300km. Just terrain scenario for all longitudes grid system. */
object Scen13All extends EScenLongMulti
{ override val title: String = "1300km all longitude terrain only scenario."
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(12, 0, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSepLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def names: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}