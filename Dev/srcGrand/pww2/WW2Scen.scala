/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg320._, pEarth._

trait WW2Scen extends EScenBasic with HSysTurnScen
{
  //override def gridSys: EGrid320Sys
  def oArmies: HCenOptLayer[Army]
}

object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid320LongFull = Terr320E0.grid
  override val terrs: HCenLayer[WTile] = Terr320E0.terrs
  override def sTerrs: HSideOptLayer[WSide] = Terr320E0.sTerrs
  override val corners: HCornerLayer = Terr320E0.corners

  val oArmies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  oArmies.unsafeSetSome(140, 520, Army(Germany))
  oArmies.unsafeSetSomes((138, 518, Army(France)))
}

object WW2Scen2 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: Grids320S0E11.type = Grids320S0E11
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn// Terr320E0.terrs
  override def sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn//Terr320E0.sTerrs
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn//Terr320E0.corners

  val oArmies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  // oArmies.unsafeSetSome(280, 524, Army(Germany))
  //oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}