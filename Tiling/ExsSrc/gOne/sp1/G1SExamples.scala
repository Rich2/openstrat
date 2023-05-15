/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

object G1SScen1 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 6, 2, 8)
  val players: SqCenOptLayer[Player] = gridSys.newSCenOptDGrider
  players.unsafeSetSome(4, 4, PlayerA)
  players.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}

object G1SScen2 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 14, 2, 22)
  val players: SqCenOptLayer[Player] = gridSys.newSCenOptDGrider
  players.unsafeSetSome(14, 18, PlayerA)
  players.unsafeSetSomes((10, 18, PlayerB), (10, 22, PlayerC), (12, 22, PlayerD))
}

object G1SScen3 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 32, 2, 40)
  val players: SqCenOptLayer[Player] = gridSys.newSCenOptDGrider
  players.unsafeSetSome(4, 4, PlayerA)
  players.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}