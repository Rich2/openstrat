package ostrat
package pReactor
import Colour._

/** A clone of the classic Atoms game */
abstract class ReactorGame(rows: Int = 8, cols: Int = 10, thePlayers:Array[Colour] = Array(Red, Green, Yellow, Blue)) 
{
  var turn:Int
  var players:Array[Colour]
  var currentPlayer:Colour
  var cellCounts:Array[Int]
  var cellColors = Array.fill[Colour](rows*cols)(Black)
  val cellSites = new Array[Array[String]](rows*cols)
  val cellNeighbours = new Array[Array[Int]](rows*cols)
  var reactionQueue = Array[Array[Int]]()

  newGame()
  
  def newGame() : Unit =
  { turn = 0
    players = thePlayers.clone()
    currentPlayer = players(0)
    cellCounts = Array.fill[Int](rows*cols)(0)
    cellColors = Array.fill[Colour](rows*cols)(Black)
    reactionQueue = Array[Array[Int]]()
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
   def isGameOver() : Boolean =
  { if (turn >= players.length) players = players.filter(cellColors.indexOf(_) != -1)
    if (players.length < 2) 
    { reactionQueue.drop(reactionQueue.length)
      true
    } else {
      false
    }
  }
  def turnComplete() : Unit =
  { turn += 1
    var currentPlayerIndex = players.indexOf(currentPlayer) + 1
    if (currentPlayerIndex >= players.length) currentPlayerIndex = 0
    currentPlayer = players(currentPlayerIndex)
  }
  def addBallByIndex(cellIndex:Int) : Unit = 
  { if (players.length > 1) 
    { cellColors(cellIndex) = currentPlayer
      cellCounts(cellIndex) += 1
    }
  }
}
