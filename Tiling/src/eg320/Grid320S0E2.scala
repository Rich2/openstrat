/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

object Grid320S0E2 extends EGrid320MainMulti
{ ThisSys =>

  override def cGridDelta: Double = 40

  override val grids: Arr[EGridMain] = Arr(EGrid320Km.l0(), EGrid320Km.l30(), EGrid320Km.l60())
  val gridMan0: EGridMainManHead = new EGridMainManHead
  { override def sys: EGridMainMulti = ThisSys
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  val gridMan1: EGridMainMan = new EGridMainMiddleMan
  { override def sys: EGridMainMulti = ThisSys
    override def seqInd: Int = 1
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  val gridMan2: EGridMainMan = new EGridMainLastMan
  { override def sys: EGridMainMulti = ThisSys
    override def seqInd: Int = 2
    override def outSteps(r: Int, c: Int): HStepCenArr = HStepCenArr()
  }

  override val gridMans: Arr[EGridMainMan] = Arr(gridMan0, gridMan1, gridMan2)

  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???
}

object Scen320S0E2 extends EScenBasic
{ override val gridSys: EGridMainSys = Grid320S0E2
  override val terrs: HCenDGrid[WTile] = Terr0() ++ EGrid320Km.terr30() ++ EGrid320Km.terr60()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}