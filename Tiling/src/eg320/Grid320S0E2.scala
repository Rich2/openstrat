/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, geom._, pEarth._

object Grid320S0E2 extends EGrid320MainMulti
{
  val gridMan0: EGridMainManHead = new EGridMainManHead
  { override val grid: EGrid320Main = EGrid320Km.l0()
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  val gridMan1: EGridMainMan = new EGridMainMiddleMan
  { override val grid: EGrid320Main = EGrid320Km.l30()
    override val arrIndex: Int = gridMan0.numTiles
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
    override def offset: Vec2 = Vec2(0, 40)
  }

  val gridMan2: EGridMainMan = new EGridMainLastMan
  { override val grid: EGrid320Main = EGrid320Km.l60()
    override val arrIndex: Int = gridMan0.numTiles * 2
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
    override def offset: Vec2 = Vec2(0, 80)
  }

  override val gridMans: Arr[EGridMainMan] = Arr(gridMan0, gridMan1, gridMan2)

  /** The grids of this tile gird system. */
  override val grids: Arr[EGrid] = gridMans.map(_.grid)


  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

}

object Scen320S0E2 extends EScenBasic
{ override val gridSys: EGridMainSys = Grid320S0E2
  override val terrs: HCenDGrid[WTile] = Terr0() ++ EGrid320Km.terr30() ++ EGrid320Km.terr60()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}