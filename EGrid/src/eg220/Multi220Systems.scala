/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, pEarth._

/** 2 Grid system for 0E and 30E */
object Grids220S0E1 extends EGrid220LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] =  EGrid220.grids(2, 0, 132)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 55
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
}

/** Scenario for 2 Grid system for 0E and 30E */
object Scen220s0e1 extends EScenLongMulti
{ override val title: String = "220km 0E - 30E"
  override implicit val gridSys: EGrid220LongMulti = Grids220S0E1
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideLayer[WSide] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}
//
///** China - Japan. 3 Grid system for 90E, 120E and 150E. */
//object Grids220ChinaJapan extends EGrid220LongMulti
//{ override val grids: RArr[EGridLongFull] = EGrid220.grids(3, 3,126)
//  override def headGridInt: Int = 3
//  override def gridsXSpacing: Double = 40
//  override val gridMans: RArr[EGridLongMan] = iToMap(2)(EGridLongMan(_, this))
//  override def adjTilesOfTile(tile: HCen): HCenArr = ???
//}
//
///** Scenario for 3 220km grid system for 0E, 30E and 60E */
//object Scen220ChinaJapan extends EScenLongMulti
//{ override val title: String = "220km 90E - 150E"
//  implicit override val gridSys: EGrid220LongMulti = Grids220ChinaJapan
//  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** 4 220km grid system for 30W 0E, 30E and 60E. */
//object Grids220S11E2 extends EGrid220LongMulti
//{ ThisSys =>
//  override def gridsXSpacing: Double = 40
//  override val grids: RArr[EGridLongFull] = EGrid220.grids(4, 11, 126)
//  override val gridMans: RArr[EGridLongMan] = iToMap(3)(EGridLongMan(_, ThisSys))
//  override val headGridInt: Int = 11
//}
//
///** Scenario for 4 220km grid system for 30W 0E, 30E and 60E. */
//object Scen220S11E2 extends EScenLongMulti
//{ override val title: String = "220km 30W - 60E"
//  override implicit val gridSys: EGrid220LongMulti = Grids220S11E2
//  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** 8 Grid system from 60W to 150E. */
//object Grids220S10E5 extends EGrid220LongMulti
//{ ThisSys =>
//  override def gridsXSpacing: Double = 40
//  override val grids: RArr[EGridLongFull] =EGrid220.grids(8, 10, 130)
//  override val gridMans: RArr[EGridLongMan] = iToMap(7)(EGridLongMan(_, ThisSys))
//  override def headGridInt: Int = 10
//}
//
///** Scenario for 8 Grid system from 60W to 150E. */
//object Scen220S10E5 extends EScenLongMulti
//{ override val title: String = "220km 60W - 150E"
//  override implicit val gridSys: EGrid220LongMulti = Grids220S10E5
//  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** All longitudes grid system. */
//object Grids220S0E11 extends EGrid220LongMulti
//{ ThisSys =>
//  override val gridsXSpacing: Double = 40
//  override val grids: RArr[EGridLongFull] = EGrid220.grids(12, 0, 130)
//  override val gridMans: RArr[EGridLongMan] = iUntilMap(12)(EGridLongMan(_, ThisSys))
//  override val headGridInt: Int = 0
//}
//
///** Just terrain scenario for all longitudes grid system. */
//object Scen220S0E11 extends EScenLongMulti
//{ override val title: String = "All longitude terrain only scenario."
//  override implicit val gridSys: EGrid220LongMulti = Grids220S0E11
//  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** North America 4 Grid system for 150W, 120W, 90W, 60W. */
//object Grids220S8E10 extends EGrid220LongMulti
//{ override val grids: RArr[EGridLongFull] = EGrid220.grids(4, 7, 128)
//  override def headGridInt: Int = 7
//  override def gridsXSpacing: Double = 40
//  override val gridMans: RArr[EGridLongMan] = iToMap(3)(EGridLongMan(_, this))
//}
//
///** Terrain only scenario for North America. 3 220km grid system for 120W, 90W and 60W */
//object Scen220S8E10 extends EScenLongMulti
//{ override val title: String = "220km 120W - 90W"
//  override implicit val gridSys: EGrid220LongMulti = Grids220S8E10
//  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}
//
///** 2 220km grid system for Northern Canada 120W and 90W. */
//object GridsNCanada extends EGrid220LongMulti
//{ override val grids: RArr[EGridLongFull] = EGrid220.grids(2, 8, 154, 158)
//  override def headGridInt: Int = 8
//  override def gridsXSpacing: Double = 40
//  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
//  override def adjTilesOfTile(tile: HCen): HCenArr = ???
//}
//
///** Scenario for 2 220km grid system for Northern Canada 120W and 90W. */
//object ScenNCanada extends EScenLongMulti
//{ override val title: String = "220km Far North Canada"
//  implicit override val gridSys: EGrid220LongMulti = GridsNCanada
//  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
//  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
//  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
//}