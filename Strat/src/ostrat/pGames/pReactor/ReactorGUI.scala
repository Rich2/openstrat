/* Copyright 2018 w0d. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pReactor

import geom._, pCanv._, Colour._

case class ReactorGUI (canv: CanvasPlatform) extends CanvasSimple("chainreactor..")
{
  val size = 40  //cell size in pixels
  val rows = 8
  val cols = 10
  var turn = 0
  var players = Array(Red, Green, Yellow, Blue)
  var currentPlayer = players(0)
  var cellCounts = Array.fill[Int](rows*cols)(0)
  var cellColors = Array.fill[Colour](rows*cols)(Black)
  val cellNeighbours = new Array[Array[Int]](80)
  var reactionQueue = Array[Int]()
  def gameBtn(str: String, cmd: MouseButton => Unit) =
    Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5, 135 vv size*rows+size).subjAll(MButtonCmd(cmd), White, 3, Black, 25, str)
  init()
  
  def init() : Unit =
  { 
    repaint(List(
      Rectangle(width, height, 0 vv 0).fill(Colour(0xFF181818)), 
      gameBtn("new | load | save", (mb: MouseButton) => { deb("3") })
    ))
    turn = 0
    players = Array(Red, Green, Yellow, Blue)
    currentPlayer = players(0)
    reactionQueue = Array[Int]()
    for( r <- 0 to rows-1; c <- 0 to cols-1)
    {
      val index = c+cols*r
      cellCounts(index) = 0
      cellColors(index) = Black
      drawBalls(size*c vv size*r, cellColors(index), cellCounts(index))
      cellNeighbours(index) = Array[Int]()
      if (c>0) cellNeighbours(index) = (index-1) +: cellNeighbours(index)
      if (r>0) cellNeighbours(index) = (index-cols) +: cellNeighbours(index)
      if (c<(cols-1)) cellNeighbours(index) = (index+1) +: cellNeighbours(index)
      if (r<(rows-1)) cellNeighbours(index) = (index+cols) +: cellNeighbours(index)
    }
    canv.polyFill(Rectangle.fromBL(size/2, size/2, -size vv -size).fill(currentPlayer))
  }
  def drawBalls(loc:Vec2, color:Colour, count:Int) : Unit =
  {
    canv.polyFill(Rectangle.fromBL(size-1, size-1, loc).fill(Black))
    if (count==2||count==4||count==5) canv.shapeFill(Circle.fill(size/4, color, loc+((size/4) vv (size/4))))
    if (count==1||count==3||count==5) canv.shapeFill(Circle.fill(size/4, color, loc+((size/2) vv (size/2))))
    if (count==2||count==4||count==5) canv.shapeFill(Circle.fill(size/4, color, loc+((3*size/4) vv (3*size/4))))
    if (count==3||count==4||count==5) canv.shapeFill(Circle.fill(size/4, color, loc+((3*size/4) vv (size/4))))
    if (count==3||count==4||count==5) canv.shapeFill(Circle.fill(size/4, color, loc+((size/4) vv (3*size/4))))
    if (count>5) canv.polyFill(Rectangle.fromBL(size-1, size-1, loc).fill(Pink))
  }
  def processQueue() : Unit = 
  {
    if (reactionQueue.length > 0)
    {
      val thisOne = reactionQueue(0)
      reactionQueue = reactionQueue.tail
      if (cellCounts(thisOne) >= cellNeighbours(thisOne).length) {
        cellCounts(thisOne) -= cellNeighbours(thisOne).length
        if (cellCounts(thisOne)==0) cellColors(thisOne) = Black
        drawBalls(size*(thisOne % cols) vv size*(thisOne / cols), currentPlayer, cellCounts(thisOne))
        for ( x <- cellNeighbours(thisOne) ) addBall( x )
      }
      canv.timeOut(() => ReactorGUI.this.processQueue(), 101)
    }
    else turnComplete()
    checkForGameOver()
  }
  def checkForGameOver() : Unit =
  {
    if (turn > players.length) players = players.filter(cellColors.indexOf(_) != -1)
    if (players.length < 2) 
    {
      canv.textGraphic(" Wins!", 16, 10 vv (-3*size/4), currentPlayer)
      reactionQueue.drop(reactionQueue.length)
    }
  }
  def turnComplete() : Unit =
  {
    turn += 1
    var currentPlayerIndex = players.indexOf(currentPlayer) + 1
    if (currentPlayerIndex >= players.length) currentPlayerIndex = 0
    currentPlayer = players(currentPlayerIndex)
    canv.polyFill(Rectangle.fromBL(size/2, size/2, -size vv -size).fill(currentPlayer))
    canv.textGraphic(turn.toString, 11, -3*size/4 vv -3*size/4, Black)
  }
  def addBall(index:Int) : Unit = 
  {
    if (players.length > 1) 
    {
      cellColors(index) = currentPlayer
      cellCounts(index) += 1
      drawBalls(size*(index % cols) vv size*(index / cols), currentPlayer, cellCounts(index))
      reactionQueue = reactionQueue :+ index
    }
  }
  mouseUp = (v, but: MouseButton, clickList) => (v, but, clickList) match
  {
    case (v, LeftButton, cl) if((reactionQueue.length == 0) && v._1 >= 0  &&  v._1 < (size*cols)  &&  v._2 >= 0  &&  v._2 < (size*rows)) =>
    {
      val index = (v._1/size).toInt+cols*((v._2/size).toInt)
      if (currentPlayer == cellColors(index) || Black  == cellColors(index))
      {
        addBall(index)
        canv.timeOut(() => ReactorGUI.this.processQueue(), 100)
      }
    }
    case (v, LeftButton, cl) if (clickList.length>0) => init
    case (v, MiddleButton, cl) if (clickList.length>0) => loadGame
    case (v, RightButton, cl) if (clickList.length>0) => saveGame
  }
  def saveGame() : Unit = 
  {
    var saveData = ""
    saveData += turn.toString + "\n"
    //saveData += turn.toString + "\n"

    // deb(players.mkString("[", ",", "]"))
    // deb(cellCounts.mkString("[", ",", "]"))
    // deb(cellColors.mkString("[", ",", "]"))
    // deb(currentPlayer.toString)
    deb("Saved")
    canv.saveFile("test", saveData)
/*  val rows = 8
  val cols = 10
  var turn = 0
  var players = Array(Red, Green, Yellow, Blue)
  var currentPlayer = players(0)
  var cellCounts = Array.fill[Int](rows*cols)(0)
  var cellColors = Array.fill[Colour](rows*cols)(Black)
*/
  }
  def loadGame() : Unit = 
  {
    val arr = canv.loadFile("test")
    deb(arr.right.toString)
    //turn = arr.right.split("\n")(0).toInt
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
