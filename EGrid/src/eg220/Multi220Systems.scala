/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._

/** 220km terrain only scenario Europe. 2 Grid system for 0°E and 30°E. */
object Scen220Europe extends EScenLongMulti
{ override val title: String = "220km Europe 0E - 30E"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 0, 132)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 220km terrain only scenario for North America. 2 grid system for 90°W and 60°W. */
object Scen220NorthAmerica extends EScenLongMulti
{ override val title: String = "220km North America 120°W - 90°W"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 9, 154, 162)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}