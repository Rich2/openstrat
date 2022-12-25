/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import prid._, phex._, egrid._, eg80._, pEarth._

trait BcScen extends EScenFlat with HSysTurnScen
{
  def terrs: HCenLayer[WTile]
}

object BcScen2 extends BcScen
{ override def turn: Int = 0

  override implicit def gridSys: HGridSys = EGrid80.e0(446)
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override def sTerrs: HSideBoolLayer = Terr80E0.sTerrs
}
