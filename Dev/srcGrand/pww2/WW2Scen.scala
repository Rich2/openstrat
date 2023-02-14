/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg320._, pEarth._

/** Scenario for World War 2 grand strategy game. */
trait WW2Scen extends EScenBasic with HSysTurnScen
{
  //override def gridSys: EGrid320Sys
  def armies: HCenOptLayer[Army]
}

object WW2Scen
{
  def sa0(layer: HCenOptLayer[Army])(implicit sys: HGridSys): Unit =
  { layer.unsafeSetSames(Germany.ar, 140, 520, 142, 518)
    layer.unsafeSetSames(France.ar, 138, 514, 138, 518)
    layer.unsafeSetSome(140, 512, Britain.ar)
  }
}

object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid320LongFull = Terr320E0.grid
  override val terrs: HCenLayer[WTile] = Terr320E0.terrs
  override val sTerrs: HSideOptLayer[WSide] = Terr320E0.sTerrs
  override val corners: HCornerLayer = Terr320E0.corners

  val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  WW2Scen.sa0(armies)
}

object WW2Scen2 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: Grids320S0E11.type = Grids320S0E11
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn

  val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  WW2Scen.sa0(armies)
  armies.unsafeSetSames(Japan.ar, 136,5624)
}