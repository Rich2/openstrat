/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import prid._, psq._, gPlay._

object TwoScen1 extends TwoScenStart
{ implicit val grid = SqGrid(2, 6, 2, 8)
  val oPlayers: SqCenOptDGrid[Player] = grid.newSCenOptDGrider
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}

object TwoScen2 extends TwoScenStart
{ implicit val grid = SqGrid(2, 16, 2, 20)
  val oPlayers: SqCenOptDGrid[Player] = grid.newSCenOptDGrider
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}