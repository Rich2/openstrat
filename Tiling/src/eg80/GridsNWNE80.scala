/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, geom._, pEarth._

object GridsNWNE80 extends EGrid80MainMulti
{ ThisSys =>

  override def cGridDelta: Double = 40
  override val grids: Arr[EGrid] = Arr(EGrid80Km.l0b446, EGrid80Km.l30b446)

  val gridMan0: EGridMainManHead = new EGridMainManHead
  { override def sys: EGridMainMulti = ThisSys
    override val grid: EGridMain = EGrid80Km.l0b446
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  val gridMan1: EGridMainMan = new EGridMainLastMan
  { override def sys: EGridMainMulti = ThisSys
    override val seqInd: Int = 1
    override val grid: EGridMain = EGrid80Km.l30b446

    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  override val gridMans: Arr[EGridMainMan] = Arr(gridMan0, gridMan1)



  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object ScenNWNE80 extends EScenBasic
{ override val gridSys: EGridMainSys = GridsNWNE80
  override val terrs: HCenDGrid[WTile] = EuropeNW80Terr() ++ EuropeNE80Terr()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}