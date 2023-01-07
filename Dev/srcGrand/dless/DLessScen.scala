/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import prid._, phex._, egrid._, eg320._, pEarth._

trait DLessScen extends HSysTurnScen
{
  def terrs: HCenLayer[WTile]

  def sTerrs: HSideBoolLayer

  def title: String = "EScenWarm"
}

object DLessScen1 extends DLessScen
{
  override def turn: Int = 0

  override implicit val gridSys: EGrid320LongMulti = new EGrid320LongMulti { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid320.grids(2, 0, 124)

    override def headGridInt: Int = 0

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMiddleMan] = iToMap(1)(EGridLongMiddleMan(_, ThisSys))

    override def adjTilesOfTile(tile: HCen): HCenArr = ???
  }

  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
}