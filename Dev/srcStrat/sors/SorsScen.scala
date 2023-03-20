/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package sors
import prid._, phex._, egrid._, eg320._

trait SorsScen extends HSysTurnScen
{ def title: String = "DLessScen"
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideLayer[WSide]
  val corners: HCornerLayer
}

/** Scenario 1  for SorsScen. */
object SorsScen1 extends SorsScen
 { override def turn: Int = 0

   override implicit val gridSys: EGrid320LongMulti = new EGrid320LongMulti { ThisSys =>
   override val grids: RArr[EGridLongFull] = EGrid320.grids(2, 0, 124)

   override def headGridInt: Int = 0

   override def gridsXSpacing: Double = 40

   override val gridMans: RArr[EGridLongMan] = iToMap(1)(EGridLongMan(_, ThisSys))
  }

  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideLayer[WSide] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}