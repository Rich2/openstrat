/* Copyright 2018-21 w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom._, Colour._

/** A clone of the classic Atoms game */
case class ReactorGame(aRows: Int = 8, aCols: Int = 10, aPlayers:Array[Colour] = Array(Red, Green, Yellow, Blue)) 
{ val rows = aRows
  val cols = aCols
  var turn = 0
  val thePlayers = aPlayers
  var players = thePlayers.clone()
  var currentPlayer = Black
  var cellCounts = Array[Int]()
  var cellColors = Array[Colour]()
  var cellSites = Array.tabulate(rows*cols)(_ => Array[String]())    //OR Array.fill[Array[Int]](length)(Array.empty)
  var cellNeighbours = Array.tabulate(rows*cols)(_ => Array[Int]())    //OR Array.ofDim[Int](100, 0)
  var addBallQueue = Array[Int]()
  var popBallQueue = Array[Int]()
  var winner = Black
  var subscribers = Map[String, Array[Int]]()   //subscribers = Map("newBallForCell"->Array[Int](), "cellWantsToPop"->Array[Int]())
  var gameState = "none"

  def startGame(aGameState:String = "turn", aTurn:Int = 0, aPlayers:Array[Colour] = thePlayers.clone(), aCurrentPlayer:Colour = players(0), aCellCounts:Array[Int] = Array.fill[Int](rows*cols)(0),
                aCellColors:Array[Colour] = Array.fill[Colour](rows*cols)(Black)): Unit =
  { deb("startgame")
    turn = aTurn
    players = aPlayers
    currentPlayer = aCurrentPlayer
    cellCounts = aCellCounts
    cellColors = aCellColors
    addBallQueue = Array.fill[Int](rows*cols)(0)
    popBallQueue = Array.fill[Int](rows*cols)(0)
    winner = Black
    setGameState(aGameState)

    ijUntilForeach(rows)(cols){ (r, c) =>
      val index:Int = c + cols * r
      cellNeighbours(index) = new Array(0)
      cellSites(index) = new Array(0)
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
  def setGameState(newState:String): Unit =
  { gameState = newState
    //deb(newState)
  }
  def addBallByIndex(cellIndex:Int): Unit =
  { cellColors(cellIndex) = currentPlayer
    cellCounts(cellIndex) += 1
  }
  def newTurn(thisPlayer:Colour, thisCell:Int): Unit =
  { if (players.length > 1 && thisPlayer == currentPlayer)
    { if (cellColors(thisCell) == Black || cellColors(thisCell) == thisPlayer)
      { addBallQueue(thisCell) += 1
        setGameState("addBall")
      }
    }
  }
  def processAddBall():Boolean =
  { for (i <- 0 to addBallQueue.length - 1)
    { for (j <- 1 to addBallQueue(i)) addBallByIndex(i)
      if (addBallQueue(i) > 0) primePopQueue(i)
    }
    addBallQueue = Array.fill[Int](rows*cols)(0)

    if (gameState == "addBall")//popBallQueue.filter(_ != 0).length == 0)
    { true //no more processing required - essentially the current players turn has concluded(bar animations) and completeTurn() can be called
    } else
    { false // processPopBall needs to be called again in the future
    }
  }
  def processPopBall():Boolean =
  { for (i <- 0 to popBallQueue.length - 1) doThePop(i)

    popBallQueue = Array.fill[Int](rows*cols)(0)

    if (gameState == "popBall")//addBallQueue.filter(_ != 0).length == 0
    { true //no more processing required - essentially the current players turn has concluded(bar animations) and completeTurn() can be called
    } else
    { false // processAddBall needs to be called again in the future
    }
  }
  def completeTurn(): Unit =
  { turn += 1
    setGameState("turn")
    var currentPlayerIndex = players.indexOf(currentPlayer) + 1
    if (currentPlayerIndex >= players.length) currentPlayerIndex = 0
    currentPlayer = players(currentPlayerIndex)
  }
  def isGameOver(): Boolean =
  { if (turn >= players.length) players = players.filter(cellColors.indexOf(_) != -1)
    if (players.length < 2)
    { addBallQueue.drop(addBallQueue.length)
      popBallQueue.drop(popBallQueue.length)
      winner = currentPlayer
      gameState == "winner"
      true
    } else {
      false
    }
  }
  def isReadyToPop(thisCell:Int):Boolean = 
  { if (cellCounts(thisCell) >= cellNeighbours(thisCell).length) true
    else false
  }
  def primePopQueue(thisCell:Int): Unit = 
  { if (isReadyToPop(thisCell))
    { popBallQueue(thisCell) = 1
      setGameState("popBall")
    }
  }
  def doThePop(thisCell:Int): Unit = 
  { if (isReadyToPop(thisCell))
    { cellCounts(thisCell) -= cellNeighbours(thisCell).length
      if (cellCounts(thisCell)==0) cellColors(thisCell) = Black
      for ( recipientCell <- cellNeighbours(thisCell) ) addBallQueue(recipientCell) += 1
      //deb("doThePop:"+cellCounts(thisCell).toString)
      setGameState("addBall")
    }
  }
}