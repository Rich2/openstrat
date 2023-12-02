/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._

/** Scenario for 3 Grid system for 0E, 30E and 60E. */
object ScenMegaS0E2 extends EScenLongMulti
{ override val title: String = "1000km 0E - 30E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(3, 0, 82)
  override val terrs: LayerHcSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 Mega metre grid system for 30W 0E, 30E and 60E. */
object ScenMegaS11E2 extends EScenLongMulti
{ override val title: String = "Megakm 30W - 60E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 11, 82)
  override val terrs: LayerHcSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 2 grid system from 60°E to 90°E. */
object ScenMegaS3E6 extends EScenLongMulti
{ override val title: String = "Mega m 90E - 150E"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 2, 82)
  override val terrs: LayerHcSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 3 Megakm grid system for 150W, 120W, 90W and 60W */
object ScenMegaAmericas extends EScenLongMulti
{ override val title: String = "Megakm 150W - 60W"
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(4, 7, 82)
  override val terrs: LayerHcSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object ScenMegaAll extends EScenLongMulti
{ override val title: String = "Mega Metre all longitude terrain only scenario."
  override implicit val gridSys: EGridMegaLongMulti = EGridMega.multi(12, 0, 82)
  override val terrs: LayerHcSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}



/** 2 Megakm grid system for Northern Canada 120W and 90W. */
object GridsNCanada extends EGridMegaLongMulti
{ override val grids: RArr[EGridLongFull] = EGridMega.grids(2, 8, 154, 82)
  override def headGridInt: Int = 8
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
}

/** Scenario for 2 Megakm grid system for Northern Canada 120W and 90W. */
/*object ScenNCanada extends EScenLongMulti
{ override val title: String = "Megakm Far North Canada"
  implicit override val gridSys: EGridMegaLongMulti = GridsNCanada
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}*/
