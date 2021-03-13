/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package gOne
import prid._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */
trait OneScen
{ val turn: Int
  implicit val grid: HGrid
  def oPlayers: HCenArrOpt[Player]

  def turn(hts: Arr[HexAndStep]): OneScen =
  {
    val resolve: HCenArrBuff[HexAndStep] = grid.newTileBuffArr
    hts.foreach{hts => resolve.appendAt(hts.hc2, hts) }
    val resValue: HCenArrOpt[Player] = oPlayers.clone

    resolve.foreach { (r, b) => b match
      { case _ if b.length == 1 => resValue.mutMove(hts.head.hc1, r)
        case _ =>
      }
    }
    OneScen(turn + 1, grid, resValue)
  }
}

/** This trait just puts the value 0 in for the turn. */
trait UneScenStart extends OneScen
{ override val turn: Int = 0
}

object OneScen
{
  def apply(turnIn: Int, gridIn: HGrid, opIn: HCenArrOpt[Player]): OneScen = new OneScen
  { override val turn = turnIn
    override implicit val grid: HGrid = gridIn
    override def oPlayers: HCenArrOpt[Player] = opIn
  }
}

object OneScen1 extends UneScenStart
{ implicit val grid = new HGridReg(2, 6, 2, 10)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
  oPlayers.setSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

object OneScen2 extends UneScenStart
{ implicit val grid = new HGridReg(2, 10, 4, 8)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.setSome(4, 4, PlayerA)
}
