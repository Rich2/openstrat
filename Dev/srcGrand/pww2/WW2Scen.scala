/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import prid._, phex._, egrid._, eg160._, pEarth._

trait WW2Scen extends EScenBasic with HSysTurnScen
{
}

object WW2Scen1 extends WW2Scen
{ override def turn: Int = 0

  override implicit def gridSys = Terr160E0.grid// EGrid160.e0(274)// Grids320S0E11
  override val terrs: HCenLayer[WTile] = Terr160E0.terrs
  override def sTerrs: HSideOptLayer[WSide] = Terr160E0.sTerrs
  override val corners: HCornerLayer = Terr160E0.corners
}
