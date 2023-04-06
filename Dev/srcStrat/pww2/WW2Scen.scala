/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg220._, pEarth._

/** Scenario for World War 2 grand strategy game. */
trait WW2Scen extends EScenBasic with HSysTurnScen
{
  //override def gridSys: EGrid220Sys
  def armies: HCenOptLayer[Army]
}

object WW2Scen
{
  def sa0(layer: HCenOptLayer[Army])(implicit sys: HGridSys): Unit =
  {  layer.setSamesUnsafe(Germany.ar, 162,522,  160,520,  158,518,  156,520)
    layer.setSamesUnsafe(France.ar, 156,518, 154,518)
    layer.setSamesUnsafe(Britain.ar, 160,512,  158,514)
  }
}

object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid220LongFull = Terr220E0.grid
  override val terrs: HCenLayer[WTile] = Terr220E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr220E0.sTerrs
  override val corners: HCornerLayer = Terr220E0.corners

  val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  WW2Scen.sa0(armies)
}

object WW2Scen2 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys: Grids220S0E1.type = Grids220S0E1
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn

  val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer
  WW2Scen.sa0(armies)
  //armies.unsafeSetSames(Japan.ar, 136,5624)
}