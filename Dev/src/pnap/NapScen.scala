/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import prid._, phex._, egrid._, eg160._, pEarth._

trait NapScen extends EScenFlat with HSysTurnScen
{
  def terrs: HCenLayer[WTile]
}

object NapScen2 extends NapScen
{ override def turn: Int = 0

  override implicit def gridSys: HGridSys = EGrid160.e0(446)
  override val terrs: HCenLayer[WTile] = ???// Terr80E0.terrs
  override def sTerrs: HSideBoolLayer = ??? //Terr80E0.sTerrs
}
