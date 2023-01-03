/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._

/** 2 Grid system for 0E and 30E */
object Grids320S0E1 extends EGrid320LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] =  EGrid320.grids(2, 0,130)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 2 Grid system for 0E and 30E */
object Scen320s0e1 extends EScenLongMulti
{ override val gridSys: EGrid320LongMulti = Grids320S0E1
  val origTerrs = RArr(Terr320E0, Terr320E30)
  override val longs: RArr[Long320Terrs] = /*iToMap(1){i => gridSys.grids(i) }*/ RArr(Terr320E0, Terr320E30)
  override val title: String = "320km 0E - 30E"
}

/** 3 Grid system for 0E, 30E and 60E. */
object Grids320S0E2 extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(3, 0,130)//RArr(EGrid320.e0(130), EGrid320.e30(130), EGrid320.e60())
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(2)(EGridLongMan(_, this))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 3 320km grid system for 0E, 30E and 60E */
object Scen320S0E2 extends EScenLongMulti
{ override val gridSys: EGrid320LongMulti = Grids320S0E2
  override def longs: RArr[LongTerrs] = RArr(Terr320E0, Terr320E30, Terr320E60)
  override val title: String = "320km 0E - 60E"
}

/** Scenario for 4 320km grid system for 30W 0E, 30E and 60E. */
object Grids320S11E2 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = RArr(EGrid320.w30(), EGrid320.e0(130), EGrid320.e30(130), EGrid320.e60())
  override val gridMans: RArr[EGridLongMan] = iToMap(3)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 11
}

object Scen320S11E2 extends EScenLongMulti
{ override val gridSys: EGridLongMulti = Grids320S11E2
  override def longs: RArr[LongTerrs] = RArr(Terr320W30,Terr320E0, Terr320E30, Terr320E60)
  //override val terrs: HCenDGrid[WTile] = Terr320W30.terrs ++ Terr320E0.terrs ++ Terr320E30.terrs ++ Terr320E60.terrs
  //override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
  override val title: String = "320km 30W - 60E"
}


object Grids320S10E5 extends EGrid320LongMulti
{ ThisSys =>

  override def gridsXSpacing: Double = 40

  override val grids: RArr[EGridLongFull] = RArr(EGrid320.w60(), EGrid320.w30(), EGrid320.e0(130), EGrid320.e30(130), EGrid320.e60(),
    EGrid320.e90(), EGrid320.e120(), EGrid320.e150())

  override val gridMans: RArr[EGridLongMan] = iToMap(7)(EGridLongMan(_, ThisSys))

  override def headGridInt: Int = 10
}

object Scen320S10E5 extends EScenLongMulti
{ override val gridSys: EGridLongMulti = Grids320S10E5
  override val longs: RArr[LongTerrs]= RArr(Terr320W60, Terr320W30, Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150)
  override val title: String = "320km 60W - 150E"
  //  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}

object Grids320S0E11 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = iUntilMap(12)(EGrid320LongFull(130, 160, _))
  override val gridMans: RArr[EGridLongMan] = iUntilMap(12)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 0
}

object Scen320S0E11 extends EScenLongMulti
{ override val gridSys: EGridLongMulti = Grids320S0E11

  override val longs: RArr[LongTerrs] = RArr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150, Terr320E180, Terr320W150,
    Terr320W120, Terr320W90, Terr320W60, Terr320W30)
}