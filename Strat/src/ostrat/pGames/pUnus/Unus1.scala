/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._, Colour._

object Unus1
{
  object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  
  def start: UnusGrid =
  {
    val newGrid = new UnusGrid(2, 18, 2, 8)
    newGrid.setTilesAll(None)
    newGrid.fSetTile(4, 4, Some(MPlayer(PlayerA, 4 cc 4)))
    newGrid.fSetTile(8, 4, Some(MPlayer(PlayerB, 8 cc 4)))
    newGrid.fSetTile(10, 6, Some(MPlayer(PlayerC, 10 cc 6)))
    newGrid
  }
}