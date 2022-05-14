/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, geom._, pEarth._

object Grids320s0e1 extends EGrid320MainMulti
{ ThisSys =>

  override val grids: Arr[EGridMain] = Arr(EGrid320.e0(), EGrid320.e30())
  override def cGridDelta: Double = 40

  val gridMan1: EGridMainMan = new EGridMainMan
  { override def sys: EGridMainMulti = ThisSys
    override def seqInd: Int = 0
  }

  val gridMan2: EGridMainMan = new EGridMainMan
  { override def sys: EGridMainMulti = ThisSys
    override def seqInd: Int = 1
  }

  override val gridMans: Arr[EGridMainMan] = Arr(gridMan1, gridMan2)
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object Scen320s0e1 extends EScenBasic
{ override val gridSys: EGridMainSys = Grids320s0e1
  override val terrs: HCenDGrid[WTile] = Terr320E0() ++ Terr320E30()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}