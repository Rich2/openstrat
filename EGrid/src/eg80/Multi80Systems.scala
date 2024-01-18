/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._

/** 80km terrain only scenario for Europe. 2 Grid system for 0°E and 30°E */
object Scen80Europe extends EScenLongMulti
{ override val title: String = "80km 0°E - 30°E"
  override implicit val gridSys: EGrid80LongMulti = EGrid80.multi(2, 0, 416)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}