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

    override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))

    override def adjTilesOfTile(tile: HCen): HCenArr = ???
  }

  override val terrs: HCenLayer[WTile] = iUntilMap(2) { i =>
    val ft = fullTerrs(i)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs)
  }.combine

  override val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer//(2, 0)
}