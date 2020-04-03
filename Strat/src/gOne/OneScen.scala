package ostrat
package gOne
import pGrid._

trait OneScen
{
  def grid: HexGrid
  def players: OptRefs[Player]
}

object OneScen1$ extends OneScen
{
  implicit val grid = new HexGridReg(2, 6, 2, 10)
  val players: OptRefs[Player] = grid.newOptRefs[Player]
  players.gridSetSome(4, 4, PlayerA)
  players.gridSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}
