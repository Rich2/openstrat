/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._

trait PeriScenStart extends HSysScen
{ def title: String = "PeriScen"
  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
}

trait PeriScen extends HSysScen
{ def title: String = "PeriScen"
  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
  val armies: HCenOptLayer[Army]
}

object PeriScen{
  def apply(inp: PeriScenStart): PeriScen = {
    //val lands = inp.gridSys.ifMap
   ???
  }
}