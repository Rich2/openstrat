/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pdiscov
import prid._, phex._, egrid._, eg320._

trait DiscovScen extends EScenBasic with HSysTurnScen

object DiscovScen1 extends DiscovScen
{ override def turn: Int = 0
  override implicit def gridSys =  Scen320All.gridSys// Terr320E0.grid
  override val terrs: LayerHcRefSys[WTile] = Scen320All.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Scen320All.sTerrs
  override val corners: HCornerLayer = Scen320All.corners
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}

object DiscovScen2 extends DiscovScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid320LongFull = Terr320E30.grid
  override val terrs: LayerHcRefSys[WTile] = Terr320E30.terrs
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = Terr320E30.sTerrs
  override val corners: HCornerLayer = Terr320E30.corners
  override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
}