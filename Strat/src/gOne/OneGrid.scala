package ostrat
package gOne
import pGrid._, Colour._

object OneGrid
{
  object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  val grid = new HexGridReg(2, 10, 2, 6)
  implicit val arr = grid.newRefs[Option[Player]]
 // grid.setSomeTile[Player](4, 4, PlayerA)
  //grid.setSomeTile[Player](8, 4, PlayerB)
  //grid.setSomeTile[Player](10, 6, PlayerC)
}
