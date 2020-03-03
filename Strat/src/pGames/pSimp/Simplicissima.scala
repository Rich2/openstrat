/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp

class Simplicissima(startScen: SimpGridOld)
{  
  var currState: SimpGridOld = startScen
  def newTurn(moves: ArrOld[Move]): SimpGridOld =
  {   
    val newGrid: SimpGridOld = currState.resolveTurn(moves)
    currState = newGrid
    currState.copy
  }
}