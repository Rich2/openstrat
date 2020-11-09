/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gUne
import prid._

/** A scenario turn or state for Game One. Consists of just a turn number and a tile Grid. Each tile can contain a single player or can be empty. */

trait UneScen
{ val turn: Int
  implicit val grid: HGridReg
  def oPlayers: HexArrOpt[Player]

  def turn(hts: Arr[HCAndStep]): UneScen =
  {
    val resolve: HcsArr[List[HCAndStep]] = ??? // grid.newTileArr(Nil)//  .newArrayListSetDepr()
//    hts.foreach{hts => resolve.prependAt(hts.r2, hts) }
//    val resValue: TilesArrOpt[Player] = oPlayers.clone
//
//    resolve.foreach{ (r, l) => l match
//    { case List(hst) => resValue.mutMove(hst.r1, r)
//      case _ =>
//    }}
//
//    OneScen(turn + 1, grid, resValue)
    ???
  }
}

/** This trait just puts the value 0 in for the turn. */
trait UneScenStart extends UneScen
{ override val turn: Int = 0
}

object UneScen
{
  def apply(turnIn: Int, gridIn: HGridReg, opIn: HexArrOpt[Player]): UneScen = new UneScen
  { override val turn = turnIn
    override implicit val grid: HGridReg = gridIn
    override def oPlayers: HexArrOpt[Player] = opIn
  }
}

object UneScen1 extends UneScenStart
{ implicit val grid = new HGridReg(2, 6, 2, 10)
  val oPlayers: HexArrOpt[Player] = grid.newTileArrOpt
  oPlayers.mutSetSome(4, 4, PlayerA)
  oPlayers.mutSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

/*object UneScen2 extends UneScenStart
{ val arr = Array[Int](2, 10, 4, 8, 6, 6, 4, 8)
  implicit val grid = new HexGridIrr(2, arr)
  val oPlayers = grid.newTileArrOpt[Player]
  oPlayers.mutSetSome(4, 4, PlayerA)
}*/
