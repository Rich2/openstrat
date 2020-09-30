/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pReactor
import geom._, pCanv._, Colour._

/** A clone of the classic Atoms game */
case class ReactorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Reactor")
{
  var stun_turn_roomname_playername = "stunId.turnId.roomId.playerId"
  val size = 40  //cell size in pixels
  val rows = 8
  val cols = 10
  var turn = 0
  var players = Array(Red, Green, Yellow, Blue)
  var currentPlayer = players(0)
  var cellCounts = Array.fill[Int](rows*cols)(0)
  var cellColors = Array.fill[Colour](rows*cols)(Black)
  val cellSites = new Array[Array[String]](rows*cols)
  val cellNeighbours = new Array[Array[Int]](rows*cols)
  var reactionQueue = Array[Int]()
  var animationStep = 0.0
  var animationIndexes = Array[Int]()
  var animationType = "Scale"
  def gameBtn(str: String, cmd: MouseButton => Unit) =
    Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5, -100 vv -100).parentAll(MouseButtonCmd(cmd), White, 3, Black, 25, str)

  init()
  
  def init() : Unit =
  { 
    repaints(
      Rectangle.applyOld(width, height, 0 vv 0).fill(Colour(0xFF181818)),
      gameBtn("new | load | save", (mb: MouseButton) => { deb("3") })
    )
    animationIndexes = Array[Int]()
    animationStep = 0.0
    turn = 0
    players = Array(Red, Green, Yellow, Blue)
    currentPlayer = players(0)
    reactionQueue = Array[Int]()
    ijUntilForeach(0, rows)(0, cols){ (r, c) =>
      val index = c+cols*r
      cellCounts(index) = 0
      cellColors(index) = Black
      drawBalls(size*c vv size*r, cellColors(index), index)
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
    canv.polygonFill(Rectangle.fromBL(size/2, size/2, -size vv -size), currentPlayer)
  }
  def drawBalls(loc:Vec2, color:Colour, cellIndex:Int) : Unit =
  { deb("drawBalls"+cellIndex.toString())
    val count = cellCounts(cellIndex)
    val isAnimation = animationIndexes.contains(cellIndex)
    canv.polygonFill(Rectangle.fromBL(size-1, size-1, loc), Black)
    if (isAnimation) canv.circleFill(Circle(size/(8/animationStep), loc+getLocFromCellSite(cellIndex, count-1)), color)
    if (count >= 1 && (count != 1 && isAnimation)) canv.circleFill(Circle(size/8, loc+getLocFromCellSite(cellIndex, 0)), color)
    if (count >= 2 && (count != 2 && isAnimation)) canv.circleFill(Circle(size/8, loc+getLocFromCellSite(cellIndex, 1)), color)
    if (count >= 3 && (count != 3 && isAnimation)) canv.circleFill(Circle(size/8, loc+getLocFromCellSite(cellIndex, 2)), color)
    if (count >= 4 && (count != 4 && isAnimation)) canv.circleFill(Circle(size/8, loc+getLocFromCellSite(cellIndex, 3)), color)
    if (count >= 5 && (count != 5 && isAnimation)) canv.circleFill(Circle(size/8, loc+getLocFromCellSite(cellIndex, 4)), color)
    if (count >= 6 && (count != 6 && isAnimation)) canv.polygonFill(Rectangle.fromBL(size-1, size-1, loc), Pink)
  }
  def getLocFromCellSite(whichCell: Int, whichOne: Int) : Vec2 =
  { val pos = cellSites(whichCell)(whichOne) 
    if ("N" == pos) size/2 vv 3*size/4
    else if ("E" == pos) 3*size/4 vv size/2
    else if ("S" == pos) size/2 vv size/4
    else if ("W" == pos) size/4 vv size/2
    else 0 vv 0
  }
  def processQueue() : Unit = 
  { if (animationIndexes.length > 0)
    { deb("animateQueue")
      animationStep += 0.1
      for (animateThisIndex <- animationIndexes) drawBalls(size*(animateThisIndex % cols) vv size*(animateThisIndex / cols), currentPlayer, animateThisIndex)
      if (animationStep >= 1)
      { animationStep = 0.0
        reactionQueue = reactionQueue ++ animationIndexes
        animationIndexes = Array[Int]()
      }
      canv.timeOut(() => ReactorGUI.this.processQueue(), 25)
    }
    else 
    { deb("reactionQueue")
      if (reactionQueue.length > 0)
      {
        val thisOne = reactionQueue(0)
        reactionQueue = reactionQueue.tail
        if (cellCounts(thisOne) >= cellNeighbours(thisOne).length) {
          cellCounts(thisOne) -= cellNeighbours(thisOne).length
          if (cellCounts(thisOne)==0) cellColors(thisOne) = Black
          drawBalls(size*(thisOne % cols) vv size*(thisOne / cols), currentPlayer, thisOne)
          for ( x <- cellNeighbours(thisOne) ) addBallByIndex( x )
        }
        canv.timeOut(() => ReactorGUI.this.processQueue(), 25)
      }
      else turnComplete()
      checkForGameOver()
    }
  }
  def checkForGameOver() : Unit =
  {
    if (turn >= players.length) players = players.filter(cellColors.indexOf(_) != -1)
    if (players.length < 2) 
    {
      canv.textGraphic(" Wins!", 16, 10 vv (-3*size/4), currentPlayer)
      reactionQueue.drop(reactionQueue.length)
      () // Explicitly return unit //NB this suppresses the compiler warning "discarded non-Unit value" generated by the above line 
    }
  }
  def turnComplete() : Unit =
  {
    turn += 1
    var currentPlayerIndex = players.indexOf(currentPlayer) + 1
    if (currentPlayerIndex >= players.length) currentPlayerIndex = 0
    currentPlayer = players(currentPlayerIndex)
    canv.polygonFill(Rectangle.fromBL(size/2, size/2, -size vv -size), currentPlayer)
    canv.textGraphic(turn.toString, 11, -3*size/4 vv -3*size/4, Black)
  }
  def addBallByIndex(cellIndex:Int) : Unit = 
  { deb("addBallByIndex(cellIndex="+cellIndex+")")
    if (players.length > 1) 
    {
      cellColors(cellIndex) = currentPlayer
      cellCounts(cellIndex) += 1
      animationIndexes = animationIndexes :+ cellIndex
      animationStep = 0.0
      drawBalls(size*(cellIndex % cols) vv size*(cellIndex / cols), currentPlayer, cellIndex)
      var s=""
      for ( x <- animationIndexes) s=s+x+","
      deb(s)
      //reactionQueue = reactionQueue :+ cellIndex
    }
  }
  mouseUp =
    {
      case (LeftButton, cl, v) if((reactionQueue.length == 0 && animationIndexes.length == 0) && v._1 >= 0  &&  v._1 < (size*cols)  &&  v._2 >= 0  &&  v._2 < (size*rows)) =>
      {
        val clickedCellIndex = (v._1/size).toInt+cols*((v._2/size).toInt)
        if (currentPlayer == cellColors(clickedCellIndex) || Black  == cellColors(clickedCellIndex))
        {
          addBallByIndex(clickedCellIndex)
          canv.timeOut(() => ReactorGUI.this.processQueue(), 100)
        }
      }
      case (LeftButton, cl, v) if (cl.length > 0) => init()
      case (MiddleButton, cl, v) if (cl.length > 0) => loadGame()
      case (RightButton, cl, v) if (cl.length > 0) => saveGame()
      case (_, _, _) => deb("uncaptured click")
    }

  def saveGame() : Unit =
  { var saveData = "\n"
    saveData += turn.toString + "\n"
    saveData += players.mkString(",") + "\n"
    saveData += cellCounts.mkString(",") + "\n"
    saveData += cellColors.mkString(",") + "\n"
    saveData += currentPlayer.toString + "\n"
    canv.saveFile("Reactor.data", saveData)
    deb("Saved!")
  }
  def loadGame() : Unit = 
  {
    val loadData = canv.loadFile("test")//**BUG "Reactor.data")
    //deb(loadData.toString)
    if (loadData.isGood)
    {
      loadData.forGood(i=>deb(i.toString))
      //turn = loadData.toString.split("\n")(1).toInt  //loadData.right.split("\n")(0).toInt
      deb("turn == " + turn)
      val c = Colour.strToValue("Red")
      deb(c.toString)
      //players = loadData.toString.split("\n")(2).split(",").map[Colour](c => Colour.strToValue(c))
      deb("players == " + players.toString)
    } else {
      deb("bad filename?")
    }
    //canv.textGraphic(turn.toString, 11, -3*size/4 vv -3*size/4, Black)
  }
}
  //   def button3(str: String, cmd: MouseButton => Unit) =
  //     Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5).subjAll(MButtonCmd(cmd), White, 3, Black, 25, str)
  //     def saveCmd = (mb: MouseButton) => { setStatus("Saved"); canv.saveFile(saveName, view.str) }
  // def bSave = button3("save", saveCmd)
//  var currentPlayer = p1 //
//  sealed class player(colour:Colour) Extends Colour(colour) 
//  object p1 extends player(Red)
//  object p2 extends player(Green)
//  object p3 extends player(Yellow)
//  object p4 extends player(Blue)

//          val r = (v._2/size).toInt
//          val c = (v._1/size).toInt
