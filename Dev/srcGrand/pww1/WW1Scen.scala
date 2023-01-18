/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import prid._, phex._, egrid._, eg80._, pEarth._

trait WW1Scen extends EScenBasic with HSysTurnScen
{
}

object WW1Scen1 extends WW1Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid80LongPart = WesternFront.wfGrid

  override val terrs: HCenLayer[WTile] = WesternFront.wfTerrs

  override def sTerrs: HSideOptLayer[WSide] = WesternFront.wfSTerrs

  override def sTerrsDepr: HSideBoolLayer = WesternFront.wfSTerrsDepr

  override val corners: HCornerLayer = WesternFront.wfCorners
}

object WW1Scen2 extends WW1Scen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid80Long = EGrid80.e0(446)
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override def sTerrs: HSideOptLayer[WSide] = Terr80E0.sTerrs
  override def sTerrsDepr: HSideBoolLayer = Terr80E0.sTerrsDepr
  override val corners: HCornerLayer = Terr80E0.corners
}