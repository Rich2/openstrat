/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import Colour._

object Simp1
{
  object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  
  def start: SimpGrid =
  {
    val newGrid = new SimpGrid(2,10, 2, 10)
    //newGrid.s
    newGrid
  }
}