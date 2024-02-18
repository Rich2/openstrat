/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._

/** 2 Grid system for 0°E and 30°E */
object Grids120S0E1 extends EGrid120LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] =  EGrid120.grids(2, 0, 300, 314)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 55
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
}

/** Scenario for 2 Grid system for 0°E and 30°E. */
object Scen120S0E1 extends EScenLongMulti
{ override val title: String = "120km 0°E - 30°E"
  override implicit val gridSys: EGrid120LongMulti = EGrid120.multi(2, 0, 300, 314)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** Scenario for 3 Grid system for 0°E, 30°E and 60°E */
object Scen120S0E2 extends EScenLongMulti
{ override val title: String = "120km 0°E - 60°E"
  override implicit val gridSys: EGrid120LongMulti = EGrid120.multi(3, 0, 300, 314)// Grids120S0E2
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

/** Scenario for 2 Grid system for 0°E and 30°E */
object Scen120S0E1North extends EScenLongMulti
{ override val title: String = "120km 0E - 30E"
  override implicit val gridSys: EGrid120LongMulti = EGrid120.multi(2, 0, 348)//Grids120S0E1North
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}