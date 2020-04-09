package ostrat
package gOne
import pGrid._

trait OneScen
{ def turn: Int
  implicit def grid: HexGrid
  //def oPlayersOld: OptRefs[Player]
  def oPlayers: TilesOptRef[Player]

  def turn(pairs: Refs[HTileAndStep]): OneScen = ???
  /*{
    val resolve: Array[List[HTileAndStep]] = grid.newArrayListSet()

    pairs.foreach{hts => resolve(grid.index(hts.r2)) ::= hts }
    val resValue: OptRefs[Player] = oPlayersOld.clone

    resolve.gridForeach{ (r, l) => l match
    {
      case List(hst) => {
        val srcIndex = grid.index(hst.r1)
        val moved: Player = resValue(srcIndex).value
        resValue.gridUnsafeSetSome(r, moved)
        resValue.unsafeSetNone(srcIndex)
      }
      case _ =>
    }}
    OneScen(turn + 1, grid, resValue, oPlayers)
  }*/
}

trait OneScenStart extends OneScen
{
  override def turn: Int = 0
}

object OneScen
{
  def apply(turnIn: Int, gridIn: HexGrid, opInOld: OptRefs[Player], opIn: TilesOptRef[Player]): OneScen = new OneScen
    { override def turn = turnIn
      override implicit def grid: HexGrid = gridIn
      override def oPlayers: TilesOptRef[Player] = opIn
    }
}

object OneScen1 extends OneScenStart
{
  implicit val grid = new HexGridReg(2, 6, 2, 10)
  val oPlayers = grid.newTilesOptRefs[Player]
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))

}
