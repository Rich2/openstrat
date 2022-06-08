/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

object Grid320S10E5 extends EGrid320WarmMulti
{ ThisSys =>

  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarm] = Arr(EGrid320.w60(),EGrid320.w30(), EGrid320.e0(), EGrid320.e30(), EGrid320.e60(), EGrid320.e90(), EGrid320.e120(), EGrid320.e150())
  override val gridMans: Arr[EGridWarmMan] = iToMap(0, 7)(EGridWarmMan(_, ThisSys))

  override def headGridInt: Int = 10
}

object Scen320S10E5 extends EScenWarm
{ override val gridSys: EGridWarmSys = Grid320S10E5
  override val terrs: HCenDGrid[WTile] = Terr320W60.terrs ++ Terr320W30.terrs ++ Terr320E0.terrs ++ Terr320E30.terrs ++ Terr320E60.terrs ++
    Terr320E90.terrs ++ Terr320E120.terrs ++ Terr320E150.terrs
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}

object Grid320S0E11 extends EGrid320WarmMulti
{ ThisSys =>

  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarm] = iUntilMap(0, 12)(EGrid320Warm(138, 160, _))
  override val gridMans: Arr[EGridWarmMan] = iUntilMap(0, 12)(EGridWarmMan(_, ThisSys))
  override def headGridInt: Int = 0
}

object Scen320S0E11 extends EScenWarmMulti
{ override val gridSys: EGridWarmMulti = Grid320S0E11

  override val warms: Arr[WarmTerrs] = Arr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150, Terr320E180, Terr320W150,
    Terr320W120, Terr320W90, Terr320W60, Terr320W30)
}