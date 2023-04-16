/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0E and 30E */
object Scen320s0e1 extends EScenLongMulti
{ override val title: String = "320km 0E - 30E"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(2, 0, 120, 166)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 320km grid system for 0E, 30E and 60E */
object Scen320ChinaJapan extends EScenLongMulti
{ override val title: String = "320km 90E - 150E"
  implicit override val gridSys: EGrid320LongMulti = EGrid320.multi(3, 3, 126, 166)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 320km grid system for 30W 0E, 30E and 60E. */
object Scen320S11E2 extends EScenLongMulti
{ override val title: String = "320km 30W - 60E"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(4, 11, 118)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 8 Grid system from 60W to 150E. */
object Grids320S10E5 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = EGrid320.grids(8, 10, 130)
  override val gridMans: RArr[EGridLongMan] = iToMap(7)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 10
}

/** Scenario for 8 Grid system from 60W to 150E. */
object Scen320S10E5 extends EScenLongMulti
{ override val title: String = "320km 60W - 150E"
  override implicit val gridSys: EGrid320LongMulti = Grids320S10E5
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** All longitudes grid system. */
object Grids320S0E11 extends EGrid320LongMulti
{ ThisSys =>
  override val gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = EGrid320.grids(12, 0, 126)
  override val gridMans: RArr[EGridLongMan] = iUntilMap(12)(EGridLongMan(_, ThisSys))
  override val headGridInt: Int = 0
}

/** Just terrain scenario for all longitudes grid system. */
object Scen320S0E11 extends EScenLongMulti
{ override val title: String = "All longitude terrain only scenario."
  override implicit val gridSys: EGrid320LongMulti = Grids320S0E11
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 3 320km grid system for 150W, 120W, 90W and 60W */
object Scen320Americas extends EScenLongMulti
{ override val title: String = "320km 150W - 60W"
  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(4, 7, 126)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 2 320km grid system for Northern Canada 120W and 90W. */
object GridsNCanada extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(2, 8, 154, 158)
  override def headGridInt: Int = 8
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
}

/** Scenario for 2 320km grid system for Northern Canada 120W and 90W. */
object ScenNCanada extends EScenLongMulti
{ override val title: String = "320km Far North Canada"
  implicit override val gridSys: EGrid320LongMulti = GridsNCanada
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}