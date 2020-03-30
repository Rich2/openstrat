package ostrat
package gOne
import pGrid._

trait OneGrid
{
  def grid: HexGrid
  def players: OptRefs[Player]
}

object OneGrid1 extends OneGrid
{
  val grid = new HexGridReg(2, 6, 2, 10)
  implicit val players: OptRefs[Player] = grid.newOptRefs[Player]
  grid.setTileSome[Player](4, 4, PlayerA)
  grid.setTileSome[Player](4, 8, PlayerB)
  grid.setTileSome[Player](6, 10, PlayerC)

}
