/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

object Grid320S10E5 extends EGrid320WarmMulti
{ ThisSys =>

  override def cGridDelta: Double = 40

  override val grids: Arr[EGridWarm] = Arr(EGrid320.w60(),EGrid320.w30(), EGrid320.e0(), EGrid320.e30(), EGrid320.e60(), EGrid320.e90(), EGrid320.e120(), EGrid320.e150())

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

  val gridMan3: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 3
  }

  val gridMan4: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 4
  }

  val gridMan5: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 5
  }

  val gridMan6: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 6
  }

  val gridMan7: EGridMainMan = new EGridMainMan
  { override def sys: EGridWarmMulti = ThisSys
    override def seqInd: Int = 7
  }

  override val gridMans: Arr[EGridMainMan] = Arr(gridMan0, gridMan1, gridMan2, gridMan3, gridMan4, gridMan5, gridMan6, gridMan7)

  override def headGridInt: Int = 10
}

object Scen320S10E5 extends EScenBasic
{ override val gridSys: EGridWarmSys = Grid320S10E5
  override val terrs: HCenDGrid[WTile] = Terr320W60() ++ Terr320W30() ++ Terr320E0() ++ Terr320E30() ++ Terr320E60() ++ Terr320E90() ++ Terr320E120() ++ Terr320E150()
  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}