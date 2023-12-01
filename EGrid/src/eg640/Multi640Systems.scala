/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0E and 30E */
object Scen640S0E1 extends EScenLongMulti
{ override val title: String = "640km 0E - 30E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(2, 0, 108)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 640km grid system for 90E, 120E and 150E */
object Scen640ChinaJapan extends EScenLongMulti
{ override val title: String = "640km 90E - 150E"
  implicit override val gridSys: EGrid640LongMulti = EGrid640.multi(3, 3, 112)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 640km grid system for 30W 0E, 30E and 60E. */
object Scen640S11E2 extends EScenLongMulti
{ override val title: String = "640km 30W - 60E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 11, 108)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 8 Grid system from 60W to 150E. */
object Scen640S10E5 extends EScenLongMulti
{ override val title: String = "640km 60W - 150E"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(8, 10, 124)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen640All extends EScenLongMulti
{ override val title: String = "640km all longitude terrain only scenario."
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(12, 0, 112)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 3 640km grid system for 150W, 120W, 90W and 60W */
object Scen640Americas extends EScenLongMulti
{ override val title: String = "640km 150W - 60W"
  override implicit val gridSys: EGrid640LongMulti = EGrid640.multi(4, 7, 108)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 2 640km grid system for Northern Canada 120W and 90W. */
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
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}
