/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package gTwo
import prid._

/** Scenario trait for Game Two. */
trait TwoScen
{ val turn: Int
  def grid: SqGrid
  def oPlayers: SqCenArrOpt[Player]
}

/** This trait just puts the value 0 in for the turn. */
trait TwoScenStart extends TwoScen
{ override val turn: Int = 0
}

object TwoScen1 extends TwoScenStart
{
  implicit val grid = new SqGrid(2, 8, 2, 10)
  val oPlayers: SqCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
  oPlayers.setSomes((4, 6, PlayerB), (6, 8, PlayerC))
}