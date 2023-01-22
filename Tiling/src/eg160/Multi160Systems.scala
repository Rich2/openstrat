/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, pEarth._

/** 2 Grid system for 0E and 30E */
object Grids160S0E1 extends EGrid160LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] = EGrid160.grids(2, 0, 276)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

/** 160km scenario for 2 Grid system for 0E and 30E */
object Scen160S0E1 extends EScenLongMulti
{ override val title: String = "160km 0E - 30E"
  override implicit val gridSys: EGrid160LongMulti = Grids160S0E1
  override lazy val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override def sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 3 Grid system for 30W, 0E and 30E */
object Grids160S11E1 extends EGrid160LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] = EGrid160.grids(3, 11, 280)
  override def headGridInt: Int = 11
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(2)(EGridLongMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

/** 160km scenario for 3 Grid system for 30W, 0E and 30E */
object Scen160s11e1 extends EScenLongMulti
{ override val title: String = "160km 0E - 30E"
  override implicit val gridSys: EGrid160LongMulti = Grids160S11E1
  override lazy val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override def sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 2 Grid system for 0E and 30E */
object Grids160S4E5 extends EGrid160LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] = EGrid160.grids(2, 4, 252, 272)
  override def headGridInt: Int = 4
  override def gridsXSpacing: Double = 40
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

/** 160km scenario for 2 Grid system for 120E and 150E */
object Scen160S4E5 extends EScenLongMulti
{ override val title: String = "160km 120E - 150E"
  override implicit val gridSys: EGrid160LongMulti = Grids160S4E5
  override lazy val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override def sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}