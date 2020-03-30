package ostrat
package gOne
import pGrid._, Colour._

object OneGrid
{ object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  val grid = new HexGridReg(2, 6, 2, 10)
  implicit val arr: OptRefs[Player] = grid.newOptRefs[Player]
  grid.setTileSome[Player](4, 4, PlayerA)
  grid.setTileSome[Player](4, 8, PlayerB)
  grid.setTileSome[Player](6, 10, PlayerC)
}
