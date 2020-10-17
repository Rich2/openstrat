/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pReactor
import Colour._

/** Computer Player for Reactor */
case class ComputerPlayer(aGameData: ReactorGame) 
{ var forThisColor = Black
  val gameData = aGameData

  def chooseTurnIndex(aThisColor:Colour):Int = 
  { forThisColor = aThisColor
    var validTurnIndexes = Array[Int]()

    //determin valid turns
    for (i <- 0 to gameData.cellColors.length - 1) 
    { val thisCellsColor = gameData.cellColors(i) 
      if (thisCellsColor == Black || thisCellsColor == forThisColor) validTurnIndexes = i +: validTurnIndexes
    }
    // players.filter(cellColors.indexOf(_) != -1)
    // if (cellCounts(thisCell) >= cellNeighbours(thisCell).length) true
    // else false
    validTurnIndexes( scala.util.Random.nextInt( validTurnIndexes.length))
  }
}
 
    // for (i <- 0 to gameData.cellColors.length - 1) gameData.cellColors(i) match
    // { case c if c == Black || c == forThisColor => i +: validTurnIndexes
    //   case _ => 
    // }
