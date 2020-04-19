/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gOne
import pGrid._

trait OneScen
{ def turn: Int
  implicit def grid: HexGridSimple
  def oPlayers: TilesArrOpt[Player]

  def turn(hts: Arr[HTileAndStep]): OneScen =
  { val resolve: TilesArr[List[HTileAndStep]] = grid.newTileArr(Nil)//  .newArrayListSetDepr()
    hts.foreach{hts => resolve.prependAt(hts.r2, hts) }
    val resValue: TilesArrOpt[Player] = oPlayers.clone

    resolve.foreach{ (r, l) => l match
    { case List(hst) => resValue.mutMove(hst.r1, r)
      case _ =>
    }}

    OneScen(turn + 1, grid, resValue)
  }
}

trait OneScenStart extends OneScen
{ override def turn: Int = 0
}

object OneScen
{
  def apply(turnIn: Int, gridIn: HexGridSimple, opIn: TilesArrOpt[Player]): OneScen = new OneScen
    { override def turn = turnIn
      override implicit def grid: HexGridSimple = gridIn
      override def oPlayers: TilesArrOpt[Player] = opIn
    }
}

object OneScen1 extends OneScenStart
{ implicit val grid = new HexGridRegSimple(2, 6, 2, 10)
  val oPlayers = grid.newTileArrOpt[Player]
  oPlayers.mutSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

object OneScen2 extends OneScenStart
{ val arr = Array[Int](2, 10, 4, 8, 6, 6, 4, 8)
  implicit val grid = new HexGridIrr(2, arr)
  val oPlayers = grid.newTileArrOpt[Player]
}