/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._

/** 640km terrain only scenario for Europe. 2 Grid system for 0°E and 30°E */
object Scen640Europe extends EScenLongMulti
{ override val title: String = "640km Europe 0°E - 30°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(2, 0, 86)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for 3 China - Japan. 3 grid system for 90°E, 120°E and 150°E */
object Scen640ChinaJapan extends EScenLongMulti
{ override val title: String = "640km China - Japan 90°E - 150°E"
  implicit override val gridSys: EGrid640LongMulti = EGrid640.multi(3, 3, 94)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for North America. 4 grid system for 150°W, 120°W, 90°W and 60°W */
object Scen640NorthAmerica extends EScenLongMulti
{ override val title: String = "640km North America 150°W - 60°W"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 7, 94)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for Africa, Middle East and Europe. 4 grid system for 30W 0°E, 30°E and 60°E. */
object Scen640Africa extends EScenLongMulti
{ override val title: String = "640km Africa 30°W - 60°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 11, 92)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for India. 2 Grid system for 60°E and 90°E */
object Scen640India extends EScenLongMulti
{ override val title: String = "640km India 60°E - 90°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(2, 2, 92)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for Atlantic and South America. 4 grid system from 90°W to 0°E. */
object Scen640Atlantic extends EScenLongMulti
{ override val title: String = "640km Atlantic 90°W - 0°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 9, 98)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for Date Line. 3 grid system from 150°E to 150°W. */
object Scen640DateLine extends EScenLongMulti
{ override val title: String = "640km Date Line 150°E - 150°W"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(3, 5, 94)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}

/** 640km terrain only scenario for all longitudes grid system. */
object Scen640All extends EScenLongMulti
{ override val title: String = "640km all longitude terrain only scenario."
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(12, 0, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = fullNamesHCenLayerSpawn
}