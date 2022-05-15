/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

object Grid320S0E2 extends EGrid320WarmMulti
{ ThisSys =>

  override val grids: Arr[EGridWarm] = Arr(EGrid320.e0(), EGrid320.e30(), EGrid320.e60())
  override def headGridInt: Int = 0
  override def cGridDelta: Double = 40

  val gridMan0: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 0
  }

  val gridMan1: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 1
  }

  val gridMan2: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 2
  }

  override val gridMans: Arr[EGridMainMan] = Arr(gridMan0, gridMan1, gridMan2)

  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???
}

object Scen320S0E2 extends EScenBasic
{ override val gridSys: EGridWarmSys = Grid320S0E2
  override val terrs: HCenDGrid[WTile] = Terr320E0() ++ Terr320E30() ++ Terr320E60()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}