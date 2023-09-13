/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._, util.Random

trait PeriScenBase extends HSysScen
{
  def title: String = "PeriScen"

  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
}

trait PeriScenStart extends PeriScenBase
{
  val nations: RArr[Nation]
}