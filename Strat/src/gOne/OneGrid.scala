package ostrat
package gOne
import pGrid._, Colour._

object OneGrid
{
  object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  val grid = new HexGridReg(2, 10, 2, 6)
}
