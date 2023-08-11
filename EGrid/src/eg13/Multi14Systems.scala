/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._

/** Scenario for 3 Grid system for 0E, 30E and 60E. */
object Scen13s0e2 extends EScenLongMulti
{ override val title: String = "1300km 0E - 30E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(3, 0, 86)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 13km grid system for 30W 0E, 30E and 60E. */
object Scen13S11E2 extends EScenLongMulti
{ override val title: String = "13km 30W - 60E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 11, 86)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 8 Grid system from 60W to 150E. */
/*object Scen13S10E5 extends EScenLongMulti
{ override val title: String = "13km 60W - 150E"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(8, 10, 86)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen13All extends EScenLongMulti
{ override val title: String = "All longitude terrain only scenario."
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(12, 0, 86)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 3 13km grid system for 150W, 120W, 90W and 60W */
object Scen13Americas extends EScenLongMulti
{ override val title: String = "13km 150W - 60W"
  override implicit val gridSys: EGrid13LongMulti = EGrid13.multi(4, 7, 86)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 2 13km grid system for Northern Canada 120W and 90W. */
object GridsNCanada extends EGrid13LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid13.grids(2, 8, 154, 86)
  override def headGridInt: Int = 8
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
}

/** Scenario for 2 13km grid system for Northern Canada 120W and 90W. */
object ScenNCanada extends EScenLongMulti
{ override val title: String = "13km Far North Canada"
  implicit override val gridSys: EGrid13LongMulti = GridsNCanada
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}*/
