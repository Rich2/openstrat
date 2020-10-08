package ostrat
package pReactor
import Colour._

/** A clone of the classic Atoms game */
abstract class ReactorGame() 
{ val rows:Int
  val cols:Int
  var turn:Int
  var players:Array[Colour]
  var currentPlayer:Colour
  var cellCounts:Array[Int]
  var cellColors:Array[Colour]
  val cellSites:Array[Array[String]]
  val cellNeighbours:Array[Array[Int]]
  var addBallQueue:Array[Int]
  var winner:Colour
  var subscribers:Map[String, Array[Int]]

  def newGame(aRows: Int = 8, aCols: Int = 10, aPlayers:Array[Colour] = Array(Red, Green, Yellow, Blue)): Unit =
  { val rows = aRows
    val cols = aCols
    val thePlayers = aPlayers
    turn = 0
    players = thePlayers.clone()
    currentPlayer = players(0)
    cellCounts = Array.fill[Int](rows*cols)(0)
    cellColors = Array.fill[Colour](rows*cols)(Black)
    addBallQueue = Array[Int]()
    winner = Black
    
    subscribers = Map("newBallForCell"->Array[Int](), "cellWantsToPop"->Array[Int]())

    ijUntilForeach(0, rows)(0, cols)
    { (r, c) =>
      val index:Int = c + cols * r
      cellNeighbours(index) = Array[Int]()
      cellSites(index) = Array[String]()
      if (c>0) 
      { cellNeighbours(index) = (index-1) +: cellNeighbours(index)
        cellSites(index) = "W" +: cellSites(index)
      }
      if (r>0) 
      { cellNeighbours(index) = (index-cols) +: cellNeighbours(index)
        cellSites(index) = "S" +: cellSites(index)
      }
      if (c<(cols-1)) 
      { cellNeighbours(index) = (index+1) +: cellNeighbours(index)
        cellSites(index) = "E" +: cellSites(index)
      }
      if (r<(rows-1)) 
      { cellNeighbours(index) = (index+cols) +: cellNeighbours(index)
        cellSites(index) = "N" +: cellSites(index)
      }
    }
  }
  def addBallByIndex(cellIndex:Int): Unit =
  { cellColors(cellIndex) = currentPlayer
    cellCounts(cellIndex) += 1
  }
  def newTurn(thisPlayer:Colour, thisCell:Int): Unit =
  { if (players.length > 1 && thisPlayer == currentPlayer) 
    { if (cellColors(thisCell) == Black || cellColors(thisCell) == thisPlayer) addBallQueue = Array(thisCell)
    }
  }
  def processTurn():Boolean = 
  { val oldAddBallQueue:Array[Int] = addBallQueue.clone
    for (i <- 0 to addBallQueue.length - 1) addBallByIndex(addBallQueue(i))
    addBallQueue = Array[Int]()
    for (i <- oldAddBallQueue) doThePop(i)
    if (addBallQueue.length == 0) true //no more processing required - essentially the current players turn has concluded(bar animations) and completeTurn() can be called
    else false // processTurn needs to be called again in the future
  }
  def completeTurn(): Unit =
  { if (addBallQueue.length == 0)
    { turn += 1
      var currentPlayerIndex = players.indexOf(currentPlayer) + 1
      if (currentPlayerIndex >= players.length) currentPlayerIndex = 0
      currentPlayer = players(currentPlayerIndex)
    }
  }
  def isGameOver(): Boolean =
  { if (turn >= players.length) players = players.filter(cellColors.indexOf(_) != -1)
    if (players.length < 2) 
    { addBallQueue.drop(addBallQueue.length)
      winner = currentPlayer
      true
    } else {
      false
    }
  }
  def isReadyToPop(thisCell:Int):Boolean = 
  { if (cellCounts(thisCell) >= cellNeighbours(thisCell).length) true
    else false
  }
  def doThePop(thisCell:Int): Unit = 
  { if (isReadyToPop(thisCell))
    { cellCounts(thisCell) -= cellNeighbours(thisCell).length
      if (cellCounts(thisCell)==0) cellColors(thisCell) = Black
      for ( x <- cellNeighbours(thisCell) ) addBallQueue = addBallQueue :+ x
    }
  }
}
