/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp

class Simplicissima(startScen: SimpGrid)
{  
  var currState: SimpGrid = startScen
  def newTurn(moves: Arr[Move]): SimpGrid =
  {   
    val newGrid: SimpGrid = currState.resolveTurn(moves)
    currState = newGrid
    currState.copy
  }
}