/* Copyright 2018-20 w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pReactor
import geom._, pCanv._, Colour._

/** A clone of the classic Atoms game */
case class ReactorGUI (canv: CanvasPlatform) extends CanvasNoPanels("Reactor")
{
  var stun_turn_roomname_playername = "stunId.turnId.roomId.playerId"
  val size = 40  //cell size in pixels
  val rows = 6
  val cols = 8
  val ballScale = 5
  var isTurnComplete = true
  var animationStep = 0.0
  val animationType = "Scale"
  val animationDuration = 20
  var gameData = ""

  def gameBtn(str: String, cmd: MouseButton => Unit) =
    Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5, -100 vv -100).parentAll(MouseButtonCmd(cmd), White, 3, Black, 25, str)

  val aDefaultGame = new ReactorGame(rows, cols, Array(Red, Green, Yellow, Blue))
  val computerPlayers = Array(Green, Yellow, Blue)
  val computerPlayer = new ComputerPlayer(aDefaultGame)

  newGame()

  def newGame() : Unit =
  { repaints(
      Rect(width, height, 0 vv 0).fill(Colour(0xFF181818)),
      gameBtn("new | load | save", (mb: MouseButton) => { deb("3") })
    )
    aDefaultGame.startGame()
    animationStep = 0.0
    canv.polygonFill(Rect.bl(size/2, size/2, -size vv -size), aDefaultGame.currentPlayer)
    ijUntilForeach(0, rows)(0, cols){ (r, c) =>
      val index = c+cols*r
      drawBalls(size*c vv size*r, Black, index)
    }
  }
  def drawBalls(loc:Vec2, color:Colour, cellIndex:Int) : Unit =
  { val count = aDefaultGame.cellCounts(cellIndex)
    canv.polygonFill(Rect.bl(size-1, size-1, loc), Black)
    if (count >= 1) canv.circleFill(Circle(size/ballScale, loc+getLocFromCellSite(cellIndex, 0)), color)
    if (count >= 2) canv.circleFill(Circle(size/ballScale, loc+getLocFromCellSite(cellIndex, 1)), color)
    if (count >= 3) canv.circleFill(Circle(size/ballScale, loc+getLocFromCellSite(cellIndex, 2)), color)
    if (count >= 4) canv.circleFill(Circle(size/ballScale, loc+getLocFromCellSite(cellIndex, 3)), color)
    if (count >= 5) canv.circleFill(Circle(size/ballScale, loc+getLocFromCellSite(cellIndex, 4)), color)
    if (count >= 6) canv.polygonFill(Rect.bl(size-1, size-1, loc), Pink)
  }
  def doAnimation() : Unit =
  { animationStep += 0.1
// add ball animation
    if (animationStep > 0.95) animationStep = 1    
    for (i <- 0 to aDefaultGame.addBallQueue.length - 1)
    { if (aDefaultGame.addBallQueue(i) != 0) 
      { //deb("ADD_ANIM="+animationStep.toString)
        val loc:Vec2 = size*(i % cols) vv size*(i / cols)
        if (animationStep == 0.1) drawBalls(loc, aDefaultGame.currentPlayer, i)
        for (b <- 1 to aDefaultGame.addBallQueue(i))
        { val whichBall = aDefaultGame.cellCounts(i) + b - 1
          canv.circleFill(Circle(size/(ballScale/animationStep), loc+getLocFromCellSite(i, whichBall)), aDefaultGame.currentPlayer)
        }
      }
    }
// popBall animation
    for (i <- 0 to aDefaultGame.popBallQueue.length - 1)
    { val loc = size*(i % cols) vv size*(i / cols)
      var locy = 0 vv 0
      if (aDefaultGame.isReadyToPop(i) == true) 
      { aDefaultGame.cellCounts(i) -= aDefaultGame.cellNeighbours(i).length  //fudge start//
        drawBalls(loc, aDefaultGame.currentPlayer, i)
        for (b <- aDefaultGame.cellSites(i))
        { b match 
          { case "N" => 
              { if (animationStep > 0.55) locy = loc + (0 vv 0.5*size*(animationStep-0.5)) 
                else locy = loc - (0 vv 0.25*size*(animationStep))
              }
            case "E" => 
              { if (animationStep > 0.55) locy = loc + (0.5*size*(animationStep-0.5) vv 0)
                else locy = loc - (0.25*size*(animationStep) vv 0)
              }
            case "S" => 
              { if (animationStep > 0.55) locy = loc - (0 vv 0.5*size*(animationStep-0.5))
                else locy = loc + (0 vv 0.25*size*(animationStep))
              }
            case "W" =>
              { if (animationStep > 0.55)  locy = loc - (0.5*size*(animationStep-0.5) vv 0)
              else locy = loc + (0.25*size*(animationStep) vv 0)
              }
          }
          canv.circleFill(Circle(size*(1 - animationStep)/ballScale, locy+getLocFromCellSite(i, 0, b)), aDefaultGame.currentPlayer)
        }
        aDefaultGame.cellCounts(i) += aDefaultGame.cellNeighbours(i).length  //fudge end//
      }
    }
// when animation completes check for game over otherwise continue animation
    if (animationStep >= 1)
    { animationStep = 0.0
      if (aDefaultGame.isGameOver() == true)
      { turnComplete()
        declareWinner()
      } else if (aDefaultGame.gameState == "popBall")
      { if (aDefaultGame.processPopBall() == true) 
        { if (aDefaultGame.isGameOver() == true) declareWinner()
          turnComplete()
        } else canv.timeOut(() => doAnimation(), animationDuration)
      } else if (aDefaultGame.gameState == "addBall")
      { if (aDefaultGame.processAddBall() == true) 
        { if (aDefaultGame.isGameOver() == true) declareWinner()
          turnComplete()
        } else canv.timeOut(() => doAnimation(), animationDuration)
      } else deb("***-- ITS A WRONG-UN! --*********************************************************")
    } else canv.timeOut(() => doAnimation(), animationDuration)
  }
  def getLocFromCellSite(whichCell: Int, whichOne: Int, whichPos: String = "") : Vec2 =
  { var pos = ""
    if (whichPos != "") pos = whichPos
    else pos = if (whichOne < aDefaultGame.cellSites(whichCell).length) aDefaultGame.cellSites(whichCell)(whichOne) else "C"
    pos match 
    { case "N" => size/2 vv 3*size/4
      case "E" => 3*size/4 vv size/2
      case "S" => size/2 vv size/4
      case "W" => size/4 vv size/2
      case _ => size/2 vv size/2
    }
  } 
  def declareWinner() : Unit =
  { if (aDefaultGame.turn >= aDefaultGame.players.length) aDefaultGame.players = aDefaultGame.players.filter(aDefaultGame.cellColors.indexOf(_) != -1)
    if (aDefaultGame.players.length < 2) canv.textGraphic(" Wins!", 16, 10 vv (-3*size/4), aDefaultGame.currentPlayer)
  }
  def turnComplete() : Unit =
  { aDefaultGame.completeTurn()
    isTurnComplete = true
    deb("turnComplete")
    canv.polygonFill(Rect.bl(size/2, size/2, -size vv -size), aDefaultGame.currentPlayer)
    canv.textGraphic(aDefaultGame.turn.toString, 11, -3*size/4 vv -3*size/4, Black)
    if (computerPlayers.indexOf(aDefaultGame.currentPlayer) != -1)
    { aDefaultGame.newTurn(aDefaultGame.currentPlayer, computerPlayer.chooseTurnIndex(aDefaultGame.currentPlayer))
      isTurnComplete = false
      canv.timeOut(() => doAnimation(), animationDuration)
    }
  }
  mouseUp =
    { case (LeftButton, cl, v) if (isTurnComplete && v._1 >= 0  &&  v._1 < (size*cols)  &&  v._2 >= 0  &&  v._2 < (size*rows) && computerPlayers.indexOf(aDefaultGame.currentPlayer) == -1) =>
      { val clickedCellIndex = (v._1/size).toInt+cols*((v._2/size).toInt)
        if (aDefaultGame.currentPlayer == aDefaultGame.cellColors(clickedCellIndex) || Black  == aDefaultGame.cellColors(clickedCellIndex))
        { aDefaultGame.newTurn(aDefaultGame.currentPlayer, clickedCellIndex)
          isTurnComplete = false
          canv.timeOut(() => doAnimation(), animationDuration)
        }
      }
      case (LeftButton, cl, v) if (cl.length > 0) => newGame()
      case (MiddleButton, cl, v) if (cl.length > 0) => loadGame()
      case (RightButton, cl, v) if (cl.length > 0) => saveGame()
      case (_, _, _) => deb("uncaptured click")
    }
  def saveGame() : Unit =
  { deb("***********************")
    if (aDefaultGame.gameState == "turn")
    { var saveData = Sett("rows", aDefaultGame.rows).ap("cols", aDefaultGame.cols).ap("turn", aDefaultGame.turn).ap("gameState",
         aDefaultGame.gameState).ap("currentPlayer", aDefaultGame.currentPlayer).str//.ap("cellCounts", aDefaultGame.cellCounts).ap("cellColors",  aDefaultGame.cellColors).str
      for (i <-0 to aDefaultGame.rows * aDefaultGame.cols - 1) saveData += Sett("cellColors"+i.toString, aDefaultGame.cellColors(i)).str
      for (i <-0 to aDefaultGame.rows * aDefaultGame.cols - 1) saveData += Sett("cellCounts"+i.toString, aDefaultGame.cellCounts(i)).str
      canv.saveFile("Reactor.data", saveData)
      deb("Saved! =>\n"+saveData)
      gameData = saveData
    }
  }
  def loadGame() : Unit = 
  { val loadData = canv.loadFile("test")//**BUG "Reactor.data")
    //deb(loadData.toString)
    if (gameData != "")
    { var d = "" + gameData.findIntSett("rows")
      deb("rows == " + d)
      //val c = Colour.strToValue("Red")
      //deb(c.toString)
      //players = loadData.toString.split("\n")(2).split(",").map[Colour](c => Colour.strToValue(c))
      //deb("players == " + aDefaultGame.players.toString)
    } else {
      deb("no data to load")
    }
    //canv.textGraphic(turn.toString, 11, -3*size/4 vv -3*size/4, Black)
  }
}


//   def saveGame() : Unit =
//   { var saveData = "\n"
//     saveData += turn.toString + "\n"
//     saveData += players.mkString(",") + "\n"
//     saveData += cellCounts.mkString(",") + "\n"
//     saveData += cellColors.mkString(",") + "\n"
//     saveData += currentPlayer.toString + "\n"
//     canv.saveFile("Reactor.data", saveData)
//     deb("Saved!")
//   }
//   def loadGame() : Unit = 
//   {
//     val loadData = canv.loadFile("test")//**BUG "Reactor.data")
//     //deb(loadData.toString)
//     if (loadData.isGood)
//     {
//       loadData.forGood(i=>deb(i.toString))
//       //turn = loadData.toString.split("\n")(1).toInt  //loadData.right.split("\n")(0).toInt
//       deb("turn == " + turn)
//       val c = Colour.strToValue("Red")
//       deb(c.toString)
//       //players = loadData.toString.split("\n")(2).split(",").map[Colour](c => Colour.strToValue(c))
//       deb("players == " + players.toString)
//     } else {
//       deb("bad filename?")
//     }
//     //canv.textGraphic(turn.toString, 11, -3*size/4 vv -3*size/4, Black)
//   }
// }
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
