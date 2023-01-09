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
{ override val title: String = "320km 0E - 30E"
  override implicit val gridSys: EGrid320LongMulti = Grids320S0E1
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
}

/** China - Japan. 3 Grid system for 90E, 120E and 150E. */
object Grids320ChinaJapan extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(3, 3,126)
  override def headGridInt: Int = 3
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(2)(EGridLongMan(_, this))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Scenario for 3 320km grid system for 0E, 30E and 60E */
object Scen320ChinaJapan extends EScenLongMulti
{ override val title: String = "320km 90E - 150E"
  implicit override val gridSys: EGrid320LongMulti = Grids320ChinaJapan
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
}

/** 4 320km grid system for 30W 0E, 30E and 60E. */
object Grids320S11E2 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = EGrid320.grids(4, 11,130)
  override val gridMans: RArr[EGridLongMan] = iToMap(3)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 11
}

/** Scenario for 4 320km grid system for 30W 0E, 30E and 60E. */
object Scen320S11E2 extends EScenLongMulti
{ override val title: String = "320km 30W - 60E"
  override implicit val gridSys: EGrid320LongMulti = Grids320S11E2
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
}

/** 8 Grid system from 60W to 150E. */
object Grids320S10E5 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] =EGrid320.grids(8, 10,130)
  override val gridMans: RArr[EGridLongMan] = iToMap(7)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 10
}

/** Scenario for 8 Grid system from 60W to 150E. */
object Scen320S10E5 extends EScenLongMulti
{ override val title: String = "320km 60W - 150E"
  override implicit val gridSys: EGrid320LongMulti = Grids320S10E5
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
}

/** All longitudes grid system. */
object Grids320S0E11 extends EGrid320LongMulti
{ ThisSys =>
  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = EGrid320.grids(12, 0,130)
  override val gridMans: RArr[EGridLongMan] = iUntilMap(12)(EGridLongMan(_, ThisSys))
  override def headGridInt: Int = 0
}

/** Just terrain scenario for all longitudes grid system. */
object Scen320S0E11 extends EScenLongMulti
{ override val title: String = "All longitude terrain only scenario."
  override implicit val gridSys: EGrid320LongMulti = Grids320S0E11
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
}

/** North America 4 Grid system for 150W, 120W, 90W, 60W. */
object Grids320S8E10 extends EGrid320LongMulti
{ override val grids: RArr[EGridLongFull] = EGrid320.grids(4, 7,128)
  override def headGridInt: Int = 7
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(3)(EGridLongMan(_, this))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???
}

/** Terrain only scenario for North America. 3 320km grid system for 120W, 90W and 60W */
object Scen320S8E10 extends EScenLongMulti
{ override val title: String = "320km 120W - 90W"
  override implicit val gridSys: EGrid320LongMulti = Grids320S8E10
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
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
{ override val title: String = "320km Far North Canada"
  implicit override val gridSys: EGrid320LongMulti = GridsNCanada
  override lazy val terrs: HCenLayer[WTile] = fullTerrsSubHCenLayer
  override lazy val sTerrs: HSideBoolLayer = fullTerrsSubSideLayer
  val hc1 = gridSys.sideTile1(HSide(158, 9724))
  debvar(hc1)
  val hc2 = gridSys.sideTile1(HSide(159, 9725))
  debvar(hc2)
  val hc3 = gridSys.sideTile1(HSide(157, 9725))
  debvar(hc3)
}