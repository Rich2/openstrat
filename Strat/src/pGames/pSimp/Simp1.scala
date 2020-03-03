/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import pGrid._, Colour._

object Simp1
{
  object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  
  def apply(): SimpGridOld =
  {
    val newGrid = SimpGridOld.start(2, 10, 2, 6)
    newGrid.setTilesAll(None)
    newGrid.fSetTile(4, 4, Some(MPlayer(PlayerA, 4 cc 4)))
    newGrid.fSetTile(8, 4, Some(MPlayer(PlayerB, 8 cc 4)))
    newGrid.fSetTile(10, 6, Some(MPlayer(PlayerC, 10 cc 6)))
    newGrid
  }
}