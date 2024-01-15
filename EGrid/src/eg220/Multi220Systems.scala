/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._

/** 2 Grid system for 0E and 30E */
object Grids220S0E1 extends EGrid220LongMulti
{ ThisSys =>
  override val grids: RArr[EGridLongFull] =  EGrid220.grids(2, 0, 132)
  override def headGridInt: Int = 0
  override def gridsXSpacing: Double = 55
  override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
}

/** 220km terrain only scenario Europe. 2 Grid system for 0E and 30E */
object Scen220Europe extends EScenLongMulti
{ override val title: String = "220km 0E - 30E"
  override implicit val gridSys: EGrid220LongMulti = Grids220S0E1
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** 220km terrain only scenario for North America. 2 grid system for 90W and 60W */
object Scen220NorthAmerica extends EScenLongMulti
{ override val title: String = "320km North America 120W - 90W"
  override implicit val gridSys: EGrid220LongMulti = EGrid220.multi(2, 9, 154, 162)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}