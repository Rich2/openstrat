/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

object G1S1Scen1 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 6, 2, 8)
  val players: SqCenOptLayer[Player] = gridSys.newSCenOptDGrider
  players.unsafeSetSome(4, 4, PlayerA)
  players.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}

object G1S1Scen2 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 16, 2, 20)
  val players: SqCenOptLayer[Player] = gridSys.newSCenOptDGrider
  players.unsafeSetSome(4, 4, PlayerA)
  players.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}