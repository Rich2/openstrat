/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

object Grid320S10E5 extends EGrid320WarmMulti
{ ThisSys =>

  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarm] = Arr(EGrid320.w60(),EGrid320.w30(), EGrid320.e0(), EGrid320.e30(), EGrid320.e60(), EGrid320.e90(), EGrid320.e120(), EGrid320.e150())
  override val gridMans: Arr[EGridMainMan] = iToMap(0, 7)(EGridMainMan(_, ThisSys))

  override def headGridInt: Int = 10
}

object Scen320S10E5 extends EScenBasic
{ override val gridSys: EGridWarmSys = Grid320S10E5
  override val terrs: HCenDGrid[WTile] = Terr320W60() ++ Terr320W30() ++ Terr320E0() ++ Terr320E30() ++ Terr320E60() ++ Terr320E90() ++ Terr320E120() ++ Terr320E150()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}