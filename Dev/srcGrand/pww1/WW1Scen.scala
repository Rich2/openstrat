/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import prid._, phex._, egrid._, eg80._, pEarth._

trait WW1Scen extends EScenBasic with HSysTurnScen
{
}

object WW1Scen1 extends WW1Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid80LongPart = EGrid80.westernFront

  override val terrs: HCenLayer[WTile] = Terr80E0.frontTerrs

  override def sTerrs: HSideOptLayer[WSide] = ???

  override def sTerrsDepr: HSideBoolLayer = Terr80E0.frontSTerrs

  override val corners: HCornerLayer = ???
}

object WW1Scen2 extends WW1Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid80Long = EGrid80.e0(446)
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override def sTerrs: HSideOptLayer[WSide] = Terr80E0.sTerrs
  override def sTerrsDepr: HSideBoolLayer = Terr80E0.sTerrsDepr
  override val corners: HCornerLayer = ???
}