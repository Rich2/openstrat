/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0°E and 30°E. */
object Scen120Europe extends EScenLongMulti
{ override val title: String = "120km 0°E - 30°E"
  override implicit val gridSys: EGrid120LongMulti = EGrid120.multi(2, 0, 284, 286)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** Scenario for 3 Grid system for 0°E, 30°E and 60°E */
object Scen120Africa extends EScenLongMulti
{ override val title: String = "120km 30°W - 60°E"
  override implicit val gridSys: EGrid120LongMulti = EGrid120.multi(4, 11, 284, 286)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}