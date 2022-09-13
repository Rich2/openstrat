/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._

/** 2 Grid system for 0E and 30E */
object Grids320S0E1 extends EGrid320WarmMulti
{ ThisSys =>
  override val grids: Arr[EGridWarmFull] = Arr(EGrid320.e0(), EGrid320.e30())
  override def headGridInt: Int = 0
  override def cGridDelta: Double = 40
  override val gridMans: Arr[EGridWarmMan] = iToMap(1)(EGridWarmMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 2 Grid system for 0E and 30E */
object Scen320s0e1 extends EScenWarmMulti
{ override val gridSys: EGrid320WarmMulti = Grids320S0E1
  override val warms: Arr[Warm320Terrs] = Arr(Terr320E0, Terr320E30)
  override val title: String = "320km 0E - 30E"
}

/** 3 Grid system for 0E, 30E and 60E. */
object Grids320S0E2 extends EGrid320WarmMulti
{ override val grids: Arr[EGridWarmFull] = Arr(EGrid320.e0(), EGrid320.e30(), EGrid320.e60())
  override def headGridInt: Int = 0
  override def cGridDelta: Double = 40
  override val gridMans: Arr[EGridWarmMan] = iToMap(2)(EGridWarmMan(_, this))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 3 320km grid system for 0E, 30E and 60E */
object Scen320S0E2 extends EScenWarmMulti
{ override val gridSys: EGrid320WarmMulti = Grids320S0E2
  override def warms: Arr[WarmTerrs] = Arr(Terr320E0, Terr320E30, Terr320E60)
  override val title: String = "320km 0E - 60E"
}

/** Scenario for 4 320km grid system for 30W 0E, 30E and 60E. */
object Grids320S11E2 extends EGrid320WarmMulti
{ ThisSys =>
  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarmFull] = Arr(EGrid320.w30(), EGrid320.e0(), EGrid320.e30(), EGrid320.e60())
  override val gridMans: Arr[EGridWarmMan] = iToMap(3)(EGridWarmMan(_, ThisSys))
  override def headGridInt: Int = 11
}

object Scen320S11E2 extends EScenWarmMulti
{ override val gridSys: EGridWarmMulti = Grids320S11E2
  override def warms: Arr[WarmTerrs] = Arr(Terr320W30,Terr320E0, Terr320E30, Terr320E60)
  //override val terrs: HCenDGrid[WTile] = Terr320W30.terrs ++ Terr320E0.terrs ++ Terr320E30.terrs ++ Terr320E60.terrs
  //override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
  override val title: String = "320km 30W - 60E"
}


object Grids320S10E5 extends EGrid320WarmMulti
{ ThisSys =>

  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarmFull] = Arr(EGrid320.w60(), EGrid320.w30(), EGrid320.e0(), EGrid320.e30(), EGrid320.e60(), EGrid320.e90(), EGrid320.e120(), EGrid320.e150())
  override val gridMans: Arr[EGridWarmMan] = iToMap(7)(EGridWarmMan(_, ThisSys))

  override def headGridInt: Int = 10
}

object Scen320S10E5 extends EScenWarmMulti
{ override val gridSys: EGridWarmMulti = Grids320S10E5
  override val warms: Arr[WarmTerrs]= Arr(Terr320W60, Terr320W30, Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150)
  override val title: String = "320km 60W - 150E"
  //  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
}

object Grids320S0E11 extends EGrid320WarmMulti
{ ThisSys =>
  override def cGridDelta: Double = 40
  override val grids: Arr[EGridWarmFull] = iUntilMap(12)(EGrid320WarmFull(138, 160, _))
  override val gridMans: Arr[EGridWarmMan] = iUntilMap(12)(EGridWarmMan(_, ThisSys))
  override def headGridInt: Int = 0
}

object Scen320S0E11 extends EScenWarmMulti
{ override val gridSys: EGridWarmMulti = Grids320S0E11

  override val warms: Arr[WarmTerrs] = Arr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150, Terr320E180, Terr320W150,
    Terr320W120, Terr320W90, Terr320W60, Terr320W30)
}
