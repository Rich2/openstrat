/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, pEarth._

/** 2 Grid system for 0E and 30E */
object Grids220S0E1 extends EGrid220LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] =  EGrid220.grids(2, 0, 132)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 55
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
}

/** Scenario for 2 Grid system for 0E and 30E */
object Scen220s0e1 extends EScenLongMulti
{ override val title: String = "220km 0E - 30E"
  override implicit val gridSys: EGrid220LongMulti = Grids220S0E1
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideLayer[WSide] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 3 320km grid system for 120W, 90W and 60W */
object Scen220Americas extends EScenLongMulti
{ override val title: String = "320km 120W - 90W"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 9, 154, 162)
  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideLayer[WSide] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}