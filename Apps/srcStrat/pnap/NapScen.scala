/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import prid._, phex._, egrid._, eg80._

trait NapScen extends EScenBasic with HSysTurnScen
{
  override def title: String = "AD1783"
  val corps: HCenOptLayer[Corps]
}

object NapScen1 extends NapScen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid80LongFull = Terr80E0.grid
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr80E0.sTerrs
  override val corners: HCornerLayer = Terr80E0.corners

  override val corps: HCenOptLayer[Corps] = HCenOptLayer()
  corps.setSomeMut(464, 516, Corps(Britain))
  corps.setSomeMut(456, 516, Corps(France))
}

object NapScen2 extends NapScen
{ override def turn: Int = 0

  override implicit def gridSys: EGrid80LongFull = Terr80E0.grid
  override val terrs: HCenLayer[WTile] = Terr80E0.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Terr80E0.sTerrs
  override val corners: HCornerLayer = Terr80E0.corners

  override val corps: HCenOptLayer[Corps] = HCenOptLayer()
}
