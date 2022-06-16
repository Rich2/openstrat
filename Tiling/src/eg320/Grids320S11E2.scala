/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._

object Grids320S11E2 extends EGrid320WarmMulti
{ ThisSys =>
  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarm] = Arr(EGrid320.w30(), EGrid320.e0(), EGrid320.e30(), EGrid320.e60())
  override val gridMans: Arr[EGridWarmMan] = iToMap(0, 3)(EGridWarmMan(_, ThisSys))
  override def headGridInt: Int = 11
}

object Scen320S11E2 extends EScenWarmMulti
{ override val gridSys: EGridWarmMulti = Grids320S11E2
  override def warms: Arr[WarmTerrs] = Arr(Terr320W30,Terr320E0, Terr320E30, Terr320E60)
  //override val terrs: HCenDGrid[WTile] = Terr320W30.terrs ++ Terr320E0.terrs ++ Terr320E30.terrs ++ Terr320E60.terrs
  //override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
  override val title: String = "320km 30W - 60E"
}