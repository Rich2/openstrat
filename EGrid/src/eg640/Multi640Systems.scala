/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._

/** 640km terrain only scenario for Europe. 2 Grid system for 0°E and 30°E */
object Scen640Europe extends EScenLongMulti
{ override val title: String = "640km Europe 0°E - 30°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(2, 0, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 640km terrain only scenario for 3 China - Japan. 3 grid system for 90°E, 120°E and 150°E */
object Scen640ChinaJapan extends EScenLongMulti
{ override val title: String = "640km China - Japan 90°E - 150°E"
  implicit override val gridSys: EGrid640LongMulti = EGrid640.multi(3, 3, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 640km terrain only scenario for North America. 4 grid system for 150°W, 120°W, 90°W and 60°W */
object Scen640NorthAmerica extends EScenLongMulti
{ override val title: String = "640km North America 150°W - 60°W"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 7, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 640km terrain only scenario for Africa, Middle East and Europe. 4 grid system for 30W 0°E, 30°E and 60°E. */
object Scen640Africa extends EScenLongMulti
{ override val title: String = "640km Africa 30°W - 60°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 11, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 8 Grid system from 60°W to 150°E. */
object Scen640SouthAmerica extends EScenLongMulti
{ override val title: String = "640km 60°W - 150°E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(3, 9, 98)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 2 640km grid system for Northern Canada 120°W and 90°W. */
object Grids640NCanada extends EGrid640LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid640.grids(2, 8, 124)
  override def headGridInt: Int = 8
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
}

/** Scenario for 2 640km grid system for Northern Canada 120W and 90W. */
object Scen640NCanada extends EScenLongMulti
{ override val title: String = "640km Far North Canada"
  implicit override val gridSys: EGrid640LongMulti = Grids640NCanada
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen640All extends EScenLongMulti
{ override val title: String = "640km all longitude terrain only scenario."
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(12, 0, 96)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}