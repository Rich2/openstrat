/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pReactor
import Colour._

/** Computer Player for Reactor */
case class ComputerPlayer(aGameData: ReactorGame) 
{ var forThisColor = Black
  val gameData = aGameData

  def chooseTurnIndex(aForThisColor:Colour):Int = 
  { forThisColor = aForThisColor
    var validTurnIndexes = Array[Int]()
    var primedIndexes = Array[Int]()
    var myPrimedIndexes = Array[Int]()
    var theirPrimedIndexes = Array[Int]()
    var chooseFromTheseIndexes = Array[Int]()

    //determin valid turns
    for (i <- 0 to gameData.cellColors.length - 1) 
    { val thisCellsColor = gameData.cellColors(i) 
      if (thisCellsColor == Black || thisCellsColor == forThisColor) validTurnIndexes = i +: validTurnIndexes
    }

    //determin cells that are 1 ball away from popping
    for (i <- validTurnIndexes) 
    { if (isPrimed(i) == true)
      { primedIndexes = i +: primedIndexes
        if (isMyColor(i) == true) myPrimedIndexes = i +: myPrimedIndexes
        else theirPrimedIndexes = i +: theirPrimedIndexes
      }
    }
    //find my primed cells that are at risk of attack
    for (i <- myPrimedIndexes)
    { for (j <- 0 to gameData.cellNeighbours(i).length - 1)
      { if (theirPrimedIndexes.indexOf(gameData.cellNeighbours(i)(j)) != -1) chooseFromTheseIndexes = chooseFromTheseIndexes :+ i
      }

    }
    //

    //search neighbours for primed (mine and others)
    //search for islands (no neighbours)
    //favour corners that arnt protected else sides
    //am i vulnrable/can i be agressive
    //

    // players.filter(cellColors.indexOf(_) != -1)
    // if (cellCounts(thisCell) >= cellNeighbours(thisCell).length) true
    // else false
    deb("primed = "+ primedIndexes.length.toString)
    deb("my = "+ myPrimedIndexes.length.toString)
    deb("their = "+ theirPrimedIndexes.length.toString)
    if (chooseFromTheseIndexes.length != 0) deb("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    if (chooseFromTheseIndexes.length == 0) chooseFromTheseIndexes = validTurnIndexes
    chooseFromTheseIndexes( scala.util.Random.nextInt( chooseFromTheseIndexes.length))
  }
  def isMyColor(cellIndex:Int):Boolean =
  { if (gameData.cellColors(cellIndex) == forThisColor) true
    else false
  }
  def isPrimed(cellIndex:Int):Boolean =
  { if (gameData.cellCounts(cellIndex) == gameData.cellNeighbours(cellIndex).length - 1) true
    else false
  }
}
 
    // for (i <- 0 to gameData.cellColors.length - 1) gameData.cellColors(i) match
    // { case c if c == Black || c == forThisColor => i +: validTurnIndexes
    //   case _ => 
    // }
