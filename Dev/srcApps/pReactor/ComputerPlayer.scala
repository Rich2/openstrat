/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pReactor
import geom._, Colour._

/** Computer Player for Reactor */
case class ComputerPlayer(aGameData: ReactorGame) 
{ var forThisColor = Black
  val gameData = aGameData

  //** chooseTurnIndex uses a combination of rudermentary strategies to produce a valid turn for a compter player 
  def chooseTurnIndex(aForThisColor:Colour):Int = 
  { forThisColor = aForThisColor
    var validTurnIndexes = Array[Int]()
    var primedIndexes = Array[Int]()
    var myPrimedIndexes = Array[Int]()
    var theirPrimedIndexes = Array[Int]()
    var chooseFromTheseIndexes = Array[Int]()

    //determin valid turns (all cells that are Black or myColor)
    for (i <- 0 to gameData.cellColors.length - 1)
    { val thisCellsColor = gameData.cellColors(i) 
      if (thisCellsColor == Black || thisCellsColor == forThisColor) validTurnIndexes = i +: validTurnIndexes
    }

    //determin cells that are primed (1 ball away from popping)
    for (i <- 0 to gameData.cellColors.length - 1)
    { if (isPrimed(i) == true)
      { primedIndexes = i +: primedIndexes
        if (isMyColor(i) == true) myPrimedIndexes = i +: myPrimedIndexes
        else theirPrimedIndexes = i +: theirPrimedIndexes
      }
    }
    //find my primed cells that are at risk of attack (adjacent cell is a different color and is primed itself)
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