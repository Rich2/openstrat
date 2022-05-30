/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

object Grids320s0e1 extends EGrid320WarmMulti
{ ThisSys =>

  override val grids: Arr[EGridWarm] = Arr(EGrid320.e0(), EGrid320.e30())
  override def headGridInt: Int = 0
  override def cGridDelta: Double = 40
  override val gridMans: Arr[EGridWarmMan] = iToMap(0, 1)(EGridWarmMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object Scen320s0e1 extends EScenWarm
{ override val gridSys: EGridWarmSys = Grids320s0e1
  override val terrs: HCenDGrid[WTile] = Terr320E0.terrs ++ Terr320E30()
  override val sTerrs: HSideBoolDGrid = new HSideBoolDGrid(Terr320E0.sTerrs().unsafeArray ++ Terr320E30.sTerrs().unsafeArray)//gridSys.newSideBools
}