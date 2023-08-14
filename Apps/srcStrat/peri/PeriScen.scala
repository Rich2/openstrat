/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._, eg13._

trait Nation extends Coloured

object NoNation extends Nation
{
  override def colour: Colour = Colour.White
}

case class Army(nation: Nation, num: Int)

trait PeriScen extends HSysScen
{
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
  val armies: HCenLayer[Army]
}

object PeriScen1 extends PeriScen
{ override implicit val gridSys: HGridSys = Scen13All.gridSys
  override val terrs: HCenLayer[WTile] = Scen13All.terrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = Scen13All.sTerrs
  override val corners: HCornerLayer = Scen13All.corners
  override val armies: HCenLayer[Army] = ???
}