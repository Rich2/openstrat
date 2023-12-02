/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._

object Scen80s0s1 extends EScenLongMulti
{ override val title: String = "80km 0°E - 30°E"
  override implicit val gridSys: EGrid80LongMulti = EGrid80.multi(2, 0, 416)
  override val terrs: LayerHcSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}