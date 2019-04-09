/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus

class UnusGame(startScen: UnusGrid) 
{  
  var currState: UnusGrid = startScen
  def newTurn(moves: List[Move]): UnusGrid =
  {   
    val newGrid: UnusGrid = currState.resolveTurn(moves)    
    currState = newGrid
    newGrid
  }
}