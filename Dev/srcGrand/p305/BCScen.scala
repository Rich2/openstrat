/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import prid._, phex._, egrid._, eg80._, pEarth._

trait BCScen extends EScenBasic with HSysTurnScen
{val armies: HCenOptLayer[Polity]
}

object BCScen1 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid80LongMulti = EGrid80.multi(2, 0, 418)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  override val armies: HCenOptLayer[Polity] = gridSys.newHCenOptLayer
  armies.unsafeSetSames(Rome, 434,562, 434,566)
  armies.unsafeSetSames(Sparta, 418,1502)
}

object BCScen2 extends BCScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid80LongFull = Terr80E0.grid
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override val sTerrs: HSideOptLayer[WSide] = Terr80E0.sTerrs
  override val corners: HCornerLayer = Terr80E0.corners
  override val armies: HCenOptLayer[Polity] = gridSys.newHCenOptLayer
}