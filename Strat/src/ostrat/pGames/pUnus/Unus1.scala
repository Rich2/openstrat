/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import Colour._

object Unus1
{
  object PlayerA extends Player('A', Red)
  object PlayerB extends Player('B', Orange)
  object PlayerC extends Player('C', Green)
  
  def start: UnusGrid =
  {
    val newGrid = new UnusGrid(2,10, 2, 10)
    
    newGrid
  }
}