/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import prid._, phex._, egrid._, eg320._, pEarth._

trait DLessScen extends HSysTurnScen
{ def title: String = "DLessScen"
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideBoolLayer
  val offsets: CornerLayer
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

  override val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
  override val offsets: CornerLayer = gridSys.newHVertOffsetLayer
}

object DLessScen2 extends DLessScen
{
  deb("Scen 2")
  override def turn: Int = 0

  override implicit val gridSys: EGrid320Long = BritReg.grid

  override val terrs: HCenLayer[WTile] = BritReg.britTerrs
  override val sTerrs: HSideBoolLayer = BritReg.britSTerrs
  override val offsets: CornerLayer = gridSys.newHVertOffsetLayer
}