/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, pEarth._

/** 2 Grid system for 0E and 30E */
object Grids320S0E1 extends EGrid320LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] =  EGrid320.grids(2, 0,124)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 2 Grid system for 0E and 30E */
object Scen320s0e1 extends EScenLongMulti
{ override val gridSys: EGrid320LongMulti = Grids320S0E1
  val origTerrs = RArr(Terr320E0, Terr320E30)
  override val longs: RArr[Long320Terrs] = RArr(Terr320E0, Terr320E30)
  override val title: String = "320km 0E - 30E"

  override lazy val terrs: HCenLayer[WTile] = iUntilMap(2) { i =>
    val ft = fullTerrs(i)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs)
  }.combine

  override lazy val sTerrs: HSideBoolLayer = gridSys.sideBoolsFromGrids(longs.map(_.sTerrs))
}

/** 3 Grid system for 0E, 30E and 60E. */
object Grids320S0E2 extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(3, 0,130)
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
  override lazy val terrs: HCenLayer[WTile] = iUntilMap(3){ i =>
    val ft = fullTerrs(i)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs) }.combine
}

/** Scenario for 4 320km grid system for 30W 0E, 30E and 60E. */
object Grids320S11E2 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = EGrid320.grids(4, 11,130)
  override val gridMans: RArr[EGridLongMan] = iToMap(3)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 11
}

object Scen320S11E2 extends EScenLongMulti
{ override val gridSys: EGridLongMulti = Grids320S11E2
  override def longs: RArr[LongTerrs] = RArr(Terr320W30,Terr320E0, Terr320E30, Terr320E60)
  //override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
  override val title: String = "320km 30W - 60E"

  override lazy val terrs: HCenLayer[WTile] = iUntilMap(4) { i =>
    val ft = fullTerrs((i + gridSys.headGridInt) %% 12)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs)
  }.combine
}

object Grids320S10E5 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] =EGrid320.grids(8, 10,130)
  override val gridMans: RArr[EGridLongMan] = iToMap(7)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 10
}

object Scen320S10E5 extends EScenLongMulti
{ override val gridSys: EGridLongMulti = Grids320S10E5
  override val longs: RArr[LongTerrs]= RArr(Terr320W60, Terr320W30, Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150)
  override val title: String = "320km 60W - 150E"
  //  override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
  override lazy val terrs: HCenLayer[WTile] = iUntilMap(8) { i =>
    val ft = fullTerrs((i + gridSys.headGridInt) %% 12)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs)
  }.combine
}

object Grids320S0E11 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] =EGrid320.grids(12, 0,130)
  override val gridMans: RArr[EGridLongMan] = iUntilMap(12)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 0
}

object Scen320S0E11 extends EScenLongMulti
{ override val gridSys: EGridLongMulti = Grids320S0E11

  override val longs: RArr[LongTerrs] = RArr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150, Terr320E180, Terr320W150,
    Terr320W120, Terr320W90, Terr320W60, Terr320W30)

  override lazy val terrs: HCenLayer[WTile] = iUntilMap(12) { i =>
    val ft = fullTerrs(i)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs)
  }.combine
}

/** 3 Grid system for 0E, 30E and 60E. */
object Grids320S8E10 extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(3, 8,130)
  override def headGridInt: Int = 8
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(2)(EGridLongMan(_, this))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 3 320km grid system for 120W, 90W and 60W */
object Scen320S8E10 extends EScenLongMulti
{ override val gridSys: EGrid320LongMulti = Grids320S8E10
  override def longs: RArr[LongTerrs] = RArr(Terr320W120, Terr320W90, Terr320W60)
  override val title: String = "320km 120W - 90W"
  override lazy val terrs: HCenLayer[WTile] = iUntilMap(3){ i =>
    val ft = fullTerrs((i + gridSys.headGridInt) %% 12)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs) }.combine
}

object GridsNCanada extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(2, 8,156,158)
  override def headGridInt: Int = 8
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, this))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 3 320km grid system for Northern Canada 120W and 90W. */
object ScenNCanada extends EScenLongMulti
{ override val gridSys: EGrid320LongMulti = GridsNCanada
  override def longs: RArr[LongTerrs] = RArr(Terr320W120, Terr320W90)
  override val title: String = "320km Far North Canada"
  override lazy val terrs: HCenLayer[WTile] = iUntilMap(2){ i =>
    val ft = fullTerrs((i + gridSys.headGridInt) %% 12)
    gridSys.grids(i).newHCenSubLayer(ft.grid, ft.terrs) }.combine

  override lazy val sTerrs: HSideBoolLayer = gridSys.sideBoolsFromGrids(longs.map(_.sTerrs))
}