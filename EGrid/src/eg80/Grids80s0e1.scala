/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, pEarth._

object Grids80s0e1 extends EGrid80LongMulti
{ ThisSys =>

  override def gridsXSpacing: Double = 40
  override val grids: RArr[EGridLongFull] = EGrid80.grids(2, 0, 434)
  override def headGridInt: Int = 0
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object Scen80s0s1 extends EScenLongMulti//Depr
{ override implicit val gridSys: EGrid80LongMulti = Grids80s0e1
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide] = fullTerrsSideOptLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}