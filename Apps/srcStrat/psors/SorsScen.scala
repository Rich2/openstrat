/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package psors
import prid._, phex._, egrid._, eg220._

trait SorsScen extends HSysTurnScen
{ def title: String = "DLessScen"
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
}

/** Scenario 1  for SorsScen. */
object SorsScen1 extends SorsScen
 { override def turn: Int = 0

   override implicit val gridSys: EGrid220LongMulti = new EGrid220LongMulti { ThisSys =>
   override val grids: RArr[EGridLongFull] = EGrid220.grids(2, 0, 140)

   override def headGridInt: Int = 0

   override def gridsXSpacing: Double = 40

   override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  }

  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}