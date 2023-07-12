/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pdisc
import prid._, phex._, egrid._, eg160._

trait DiscScen extends EScenBasic with HSysTurnScen
{
}

object DiscScen1 extends DiscScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid160LongFull = Terr160E0.grid
  override val terrs: HCenLayer[WTile] = Terr160E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr160E0.sTerrs
  override val corners: HCornerLayer = Terr160E0.corners
}

object DiscScen2 extends DiscScen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid160LongFull = Terr160E30.grid
  override val terrs: HCenLayer[WTile] = Terr160E30.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr160E30.sTerrs
  override val corners: HCornerLayer = Terr160E30.corners
}