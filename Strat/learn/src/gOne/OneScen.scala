package ostrat
package gOne
import pGrid._

trait OneScen
{
  implicit def grid: HexGrid
  def oPlayers: OptRefs[Player]

  def turn(pairs: Refs[(Roord, HTStep)]): OneScen =
  {
    val resolve: Array[List[HTStep]] = grid.newArrayListSet()

    pairs.foreach{case (orig, st) =>
      val targR = orig.step(st)
      resolve(grid.index(targR)) ::= st
    }
    val resValue: OptRefs[Player] = oPlayers.clone
    resolve.gridForeach{ (r, l) => l match
    {
      case List(st) => {
        val srcIndex = grid.index(r.stepBack(st))
        val moved: Player = resValue(srcIndex).value
        resValue.gridSetSome(r, moved)
        resValue.setNone(srcIndex)
      }
      case _ =>
    }}
    OneScen(grid, resValue)
  }
}

object OneScen
{
  def apply(gridIn: HexGrid, opIn: OptRefs[Player]): OneScen = new OneScen
    { override implicit def grid: HexGrid = gridIn
      override def oPlayers: OptRefs[Player] = opIn
    }
}

object OneScen1 extends OneScen
{
  implicit val grid = new HexGridReg(2, 6, 2, 10)
  val oPlayers: OptRefs[Player] = grid.newOptRefs[Player]
  oPlayers.gridSetSome(4, 4, PlayerA)
  oPlayers.gridSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}
