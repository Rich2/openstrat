package ostrat
package gOne
import pGrid._

trait OneScen
{ def turn: Int
  implicit def grid: HexGrid
  def oPlayers: TilesOptRef[Player]

  def turn(hts: Arr[HTileAndStep]): OneScen =
  { val resolve: TilesRef[List[HTileAndStep]] = grid.newTilesRefInit(Nil)//  .newArrayListSetDepr()
    hts.foreach{hts => resolve.prependAt(hts.r2, hts) }
    val resValue: TilesOptRef[Player] = oPlayers.clone

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
  def apply(turnIn: Int, gridIn: HexGrid, opIn: TilesOptRef[Player]): OneScen = new OneScen
    { override def turn = turnIn
      override implicit def grid: HexGrid = gridIn
      override def oPlayers: TilesOptRef[Player] = opIn
    }
}

object OneScen1 extends OneScenStart
{ implicit val grid = new HexGridReg(2, 6, 2, 10)
  val oPlayers = grid.newTilesOptRef[Player]
  oPlayers.mutSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}