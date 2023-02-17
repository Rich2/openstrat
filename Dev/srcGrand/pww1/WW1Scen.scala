/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import prid._, phex._, egrid._, eg120._, pEarth._

trait WW1Scen extends EScenBasic with HSysTurnScen
{
  override def title: String = "WWI scenario."
  val armies: HCenOptLayer[Army]
}

object WW1Scen1 extends WW1Scen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid120LongFull = Terr120E0.grid
  override val terrs: HCenLayer[WTile] = Terr120E0.terrs
  override val sTerrs: HSideOptLayer[WSide] = Terr120E0.sTerrs
  override val corners: HCornerLayer = Terr120E0.corners
  override val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer[Army]
  //armies.unsafeSetSome(280, 516, Army(Britain))
  //armies.unsafeSetSome(280, 524, Army(Germany))

}
object WW1Scen2 extends WW1Scen
{ override def turn: Int = 0
  override implicit def gridSys: EGrid120LongFull = Terr120E30.grid
  override val terrs: HCenLayer[WTile] = Terr120E30.terrs
  override val sTerrs: HSideOptLayer[WSide] = Terr120E30.sTerrs
  override val corners: HCornerLayer = Terr120E30.corners
  override val armies: HCenOptLayer[Army] = gridSys.newHCenOptLayer[Army]
}