/* Copyright 2018-21 w0d, Rich Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pReactor
import geom._, pCanv._, Colour._, collection.mutable.Map

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

  def gameBtn(str: String, cmd: MouseButton => Unit): PolyCurveParentFull =
    Rectangle.curvedCornersCentred(str.length.max(2) * 17, 25, 5, -100 pp -100).parentAll(MouseButtonCmd(cmd), White, 3, Black, 25, str)

  var aDefaultGame = new ReactorGame(rows, cols, Array(Red, Green, Yellow, Blue))
  var computerPlayers = Array(Red, Green, Yellow, Blue)
  val computerPlayer = new ComputerPlayer(aDefaultGame)

  val player1Options = RadioGroup(Arr(RadioOption(false, "HUMAN", -180 pp 220, true), RadioOption(true, "COMPUTER", -180 pp 200, true)), 1)
  val player2Options = RadioGroup(Arr(RadioOption(false, "HUMAN", -180 pp 160, true), RadioOption(true, "COMPUTER", -180 pp 140, true)), 1)
  val player3Options = RadioGroup(Arr(RadioOption(false, "HUMAN", -180 pp 100, true), RadioOption(true, "COMPUTER", -180 pp 80, true)), 1)
  val player4Options = RadioGroup(Arr(RadioOption(false, "HUMAN", -180 pp 40, true), RadioOption(true, "COMPUTER", -180 pp 20, true)), 1)

  val player1 = Checkbox(true, "PLAYER 1", -200 pp 240, true, (player1:Checkbox) => { player1Options.setEnabled(player1.isSelected) }, myColor = Red)
  val player2 = Checkbox(true, "PLAYER 2", -200 pp 180, true, (player2:Checkbox) => { player2Options.setEnabled(player2.isSelected) }, myColor = Green)
  val player3 = Checkbox(true, "PLAYER 3", -200 pp 120, true, (player3:Checkbox) => { player3Options.setEnabled(player3.isSelected) }, myColor = Yellow)
  val player4 = Checkbox(true, "PLAYER 4", -200 pp 60, true, (player4:Checkbox) => { player4Options.setEnabled(player4.isSelected) }, myColor = Blue)

  val checkboxes = Array(player1, player2, player3, player4)
  val radioGroups = Array(player1Options, player2Options, player3Options, player4Options)

  // val playerIndicators:GraphicElems = ???

  newGame()

  def composeGUI():Unit =
  { var config:GraphicElems = Arr()
    checkboxes.foreach(chkbx => config = config ++ chkbx.toGraphicElems())
    radioGroups.foreach(rdgp => config = config ++ rdgp.toGraphicElems())
    val composition:GraphicElems = Arr(Rect(width, height, 0 pp 0).fill(Colour(0xFF181818)), gameBtn("new game", (mb: MouseButton) => { deb("3") })) ++ config ++ getCurrentPlayerIndicator()
    repaint(composition)
    ijUntilForeach(0, rows)(0, cols){ (r, c) =>
      val index = c+cols*r
      drawBalls(size*c pp size*r, aDefaultGame.cellColors(index), index)
    }
  }

  def getPlayers():Array[Colour] =
  { var playerSelection:Array[Colour]  = Array()
    computerPlayers = Array()
    iUntilForeach(0, checkboxes.length){ i => if (checkboxes(i).isSelected == true)
      { playerSelection = playerSelection :+ checkboxes(i).color
         if (radioGroups(i).selected.labelText == "COMPUTER") computerPlayers = computerPlayers :+ checkboxes(i).color
      }
     }
    debvar(playerSelection.length)
    playerSelection
  }

  def newGame(): Unit =
  { animationStep = 0.0
    aDefaultGame.startGame(aPlayers = getPlayers(), aCurrentPlayer = (getPlayers())(0))
    checkForComputerTurn()
    composeGUI()
  }

  def drawBalls(loc:Pt2, color:Colour, cellIndex:Int) : Unit =
  { val count = aDefaultGame.cellCounts(cellIndex)
    canv.polygonFill(Rect.bl(size-1, size-1, loc).fill(Black))
    if (count >= 1) canv.circleFill(Circle(size/ballScale, loc.slate(getLocFromCellSite(cellIndex, 0))).fill(color))
    if (count >= 2) canv.circleFill(Circle(size/ballScale, loc.slate(getLocFromCellSite(cellIndex, 1))).fill(color))
    if (count >= 3) canv.circleFill(Circle(size/ballScale, loc.slate(getLocFromCellSite(cellIndex, 2))).fill(color))
    if (count >= 4) canv.circleFill(Circle(size/ballScale, loc.slate(getLocFromCellSite(cellIndex, 3))).fill(color))
    if (count >= 5) canv.circleFill(Circle(size/ballScale, loc.slate(getLocFromCellSite(cellIndex, 4))).fill(color))
    if (count >= 6) canv.polygonFill(Rect.bl(size-1, size-1, loc).fill(Pink))
  }

  def doAnimation() : Unit =
  { animationStep += 0.1
    // add ball animation
    if (animationStep > 0.95) animationStep = 1
    for (i <- 0 to aDefaultGame.addBallQueue.length - 1)
    { if (aDefaultGame.addBallQueue(i) != 0)
      { val loc:Pt2 = size*(i%cols) pp size*(i/cols)
        if (animationStep == 0.1) drawBalls(loc, aDefaultGame.currentPlayer, i)
        for (b <- 1 to aDefaultGame.addBallQueue(i))
        { val whichBall = aDefaultGame.cellCounts(i)+b-1
          canv.circleFill(Circle(size/(ballScale/animationStep), loc.slate(getLocFromCellSite(i, whichBall))).fill(aDefaultGame.currentPlayer))
        }
      }
    }
    // popBall animation
    for (i <- 0 to aDefaultGame.popBallQueue.length - 1)
    { val loc = size*(i % cols) pp size*(i / cols)
      var locy = Pt2Z
      if (aDefaultGame.isReadyToPop(i) == true)
      { aDefaultGame.cellCounts(i) -= aDefaultGame.cellNeighbours(i).length  //fudge start//
        drawBalls(loc, aDefaultGame.currentPlayer, i)
        for (b <- aDefaultGame.cellSites(i))
        { b match
          { case "N" =>
            { if (animationStep > 0.55) locy = loc.addY(0.5*size*(animationStep-0.5))
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
          canv.circleFill(Circle(size*(1 - animationStep)/ballScale, locy.slate(getLocFromCellSite(i, 0, b))).
            fill(aDefaultGame.currentPlayer))
        }
        aDefaultGame.cellCounts(i) += aDefaultGame.cellNeighbours(i).length  //fudge end//
      }
    }
    // when animation completes check for game over otherwise continue animation
    if (animationStep >= 1)
    { animationStep = 0.0
      if (aDefaultGame.gameState == "popBall")
      { if (aDefaultGame.processPopBall() == true)
        { if (aDefaultGame.isGameOver() == true) declareWinner()
          else turnComplete()
        } else canv.timeOut(() => doAnimation(), animationDuration)
      } else if (aDefaultGame.gameState == "addBall")
      { if (aDefaultGame.processAddBall() == true)
        { if (aDefaultGame.isGameOver() == true) declareWinner()
          else turnComplete()
        } else {canv.timeOut(() => doAnimation(), animationDuration); composeGUI()}
      } else deb("***-- not pop or add: "+aDefaultGame.gameState+" --***")
    } else canv.timeOut(() => doAnimation(), animationDuration)
  }

  def getLocFromCellSite(whichCell: Int, whichOne: Int, whichPos: String = "") : Pt2 =
  { var pos = ""
    if (whichPos != "") pos = whichPos
    else pos = if (whichOne < aDefaultGame.cellSites(whichCell).length) aDefaultGame.cellSites(whichCell)(whichOne) else "C"
    pos match
    { case "N" => size/2 pp 3*size/4
    case "E" => 3*size/4 pp size/2
    case "S" => size/2 pp size/4
    case "W" => size/4 pp size/2
    case _ => size/2 pp size/2
    }
  }

  def declareWinner() : Unit =
  { if (aDefaultGame.turn >= aDefaultGame.players.length) aDefaultGame.players = aDefaultGame.players.filter(aDefaultGame.cellColors.indexOf(_) != -1)
    if (aDefaultGame.players.length < 2) canv.textGraphic(TextGraphic(" Wins!", 16, 10 pp (-3*size/4), aDefaultGame.currentPlayer))
  }

  /** If the current player is a computer then play its hand here */
  def checkForComputerTurn(): Unit =
  { if (computerPlayers.indexOf(aDefaultGame.currentPlayer) != -1)
    { aDefaultGame.newTurn(aDefaultGame.currentPlayer, computerPlayer.chooseTurnIndex(aDefaultGame.currentPlayer))
      isTurnComplete = false
      canv.timeOut(() => doAnimation(), animationDuration)
    }
    composeGUI() 
  }

  def turnComplete() : Unit =
  { aDefaultGame.completeTurn()
    isTurnComplete = true
    checkForComputerTurn()
  }

  def getCurrentPlayerIndicator():GraphicElems =
  { var totals = Map("Red"->0, "Yellow"->0, "Green"->0, "Blue"->0, "Black"->0)
    for(i <-0 to aDefaultGame.cellColors.length-1) totals(aDefaultGame.cellColors(i).toString) = totals(aDefaultGame.cellColors(i).toString) + aDefaultGame.cellCounts(i)
    // Array(Red,Yellow,Green,Blue).
    Arr(Rect.bl(size/4, 2*totals("Red"), -3*size/4 pp 0).fill(Red), TextGraphic(totals("Red").toString, 7, -5*size/8 pp 7*size/8-size, White),
        Rect.bl(size/4, 2*totals("Green"), -4*size/4 pp 0).fill(Green), TextGraphic(totals("Green").toString, 7, -7*size/8 pp 7*size/8-size, White),
        Rect.bl(size/4, 2*totals("Yellow"), -5*size/4 pp 0).fill(Yellow), TextGraphic(totals("Yellow").toString, 7, -9*size/8 pp 7*size/8-size, White),
        Rect.bl(size/4, 2*totals("Blue"), -6*size/4 pp 0).fill(Blue), TextGraphic(totals("Blue").toString, 7, -11*size/8 pp 7*size/8-size, White),
        Rect.bl(size/2, size/2, -size pp -size).fill(aDefaultGame.currentPlayer), TextGraphic(aDefaultGame.turn.toString, 11, -3*size/4 pp -3*size/4, Black))
  }

  mouseUp =
  {
    case (LeftButton, cl, v) if (isTurnComplete && v.x >= 0  &&  v.x < (size * cols)  &&  v.y >= 0  &&  v.y < (size * rows) &&
      computerPlayers.indexOf(aDefaultGame.currentPlayer) == -1) =>
      { val clickedCellIndex = (v.x/size).toInt+cols * ((v.y/size).toInt)
        if (aDefaultGame.currentPlayer == aDefaultGame.cellColors(clickedCellIndex) || Black  == aDefaultGame.cellColors(clickedCellIndex))
        { aDefaultGame.newTurn(aDefaultGame.currentPlayer, clickedCellIndex)
          isTurnComplete = false
          canv.timeOut(() => doAnimation(), animationDuration)
      }
    }
    case (LeftButton, cl, v) if (cl.elemsNum > 0) =>
    { if (cl(0).isInstanceOf[Checkbox])
      { cl(0).asInstanceOf[Checkbox].clicked()
        composeGUI()
      } else if (cl(0).isInstanceOf[RadioOption])
      { cl(0).asInstanceOf[RadioOption].clicked()
        composeGUI()
      } else newGame()
    }
    case (LeftButton, cl, _) => debvar(cl)
    case (MiddleButton, cl, v) if (cl.elemsNum > 0) => loadGame()
    case (RightButton, cl, v) if (cl.elemsNum > 0) => saveGame()
    case (_, _, _) => deb("uncaptured clicky")
  }

  def saveGame() : Unit =
  { if (aDefaultGame.gameState == "turn")
    { var saveData = Setting("rows", aDefaultGame.rows).ap("cols", aDefaultGame.cols).ap("turn", aDefaultGame.turn).ap("gameState",
      aDefaultGame.gameState).ap("currentPlayer", aDefaultGame.currentPlayer).str + "\n" //.ap("cellCounts", aDefaultGame.cellCounts).ap("cellColors",  aDefaultGame.cellColors).str
      for (i <-0 to aDefaultGame.rows * aDefaultGame.cols - 1) saveData += Setting("cellColors"+i.toString, aDefaultGame.cellColors(i)).str + "\n"
      for (i <-0 to aDefaultGame.rows * aDefaultGame.cols - 1) saveData += Setting("cellCounts"+i.toString, aDefaultGame.cellCounts(i)).str + "\n"
      canv.saveFile("Reactor.data", saveData)
      deb("Saved! =>\n"+saveData)
      gameData = saveData
    }
  }

  def loadGame() : Unit =
  { //val loadData = canv.loadFile("test")//**BUG "Reactor.data")
    //deb(loadData.toString)
    if (gameData != "")
    { val d = "" + gameData.findSettingInt("rows")
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