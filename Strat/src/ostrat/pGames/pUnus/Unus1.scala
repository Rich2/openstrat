/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import Colour._

object Unus1
{
  case class PlayerA(x: Int, y: Int) extends Player('A', Red)
  case class PlayerB(x: Int, y: Int) extends Player('B', Orange)
  case class PlayerC(x: Int, y: Int) extends Player('C', Green)
  
  def start: UnusGrid =
  {
    val newGrid = new UnusGrid(2, 18, 2, 8)
    newGrid.setTilesAll(None)
    newGrid.fSetTile(4, 4, Some(PlayerA(4, 4)))
    newGrid.fSetTile(8, 4, Some(PlayerB(8, 4)))
    newGrid.fSetTile(10, 6, Some(PlayerC(10, 6)))
    newGrid
  }
}