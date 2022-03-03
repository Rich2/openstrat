/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game One. */
object OneScen1 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object OneScen2 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (8, 4, PlayerB), (6, 6, PlayerC))
}

/** 3rd example Turn 0 scenario state for Game One. */
object OneScen3 extends OneScenStart
{ implicit val grid: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

object OneScen4 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 12, 2, 60)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}