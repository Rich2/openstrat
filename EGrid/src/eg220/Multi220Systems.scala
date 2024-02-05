/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._

/** 220km terrain only scenario Europe. 2 Grid system for 0°E and 30°E. */
object Scen220Europe extends EScenLongMulti
{ override val title: String = "220km Europe 0E - 30E"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 0, 132)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 220km terrain only scenario for North America. 2 grid system for 90°W and 60°W. */
object Scen220NorthAmerica extends EScenLongMulti
{ override val title: String = "220km North America 120°W - 90°W"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 9, 144, 164)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 220km terrain only scenario for North America. 2 grid system for 120°W and 90°W. */
object Scen220NorthAmerica2 extends EScenLongMulti
{ override val title: String = "220km North America 120°W - 90°W"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 8, 150, 152)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 220km terrain only scenario for Atlantic and South America. 4 Grid system from 90°W to 0°E. */
object Scen220Atlantic extends EScenLongMulti
{ override val title: String = "320km Atlantic 120°W - 30°W"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(4, 9, 154, 164)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}