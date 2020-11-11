/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package gDeux
import prid._

trait DeuxScen
{ val turn: Int
  def grid: SqGrid
  def oPlayers: SqcenArrOpt[Player]
}

/** This trait just puts the value 0 in for the turn. */
trait DeuxScenStart extends DeuxScen
{ override val turn: Int = 0
}

object DeuxScen1 extends DeuxScenStart
{
  implicit val grid = new SqGrid(2, 8, 2, 10)
  val oPlayers: SqcenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
  oPlayers.setSomes((4, 6, PlayerB), (6, 8, PlayerC))
}