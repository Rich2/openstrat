/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** E Series lessons deal with games. E1 is a super simple single player turn game.  */
case class LessonE1(canv: CanvasPlatform) extends CmdBarGui("Lesson E1")
{
  import e1._
  var state: GameState = GameState.start
  var cmd: TurnCmd = NoMove
  var statusText = "Right click to set action to Move. Left to set action to CycleColour. Press Turn button or middle click for next turn."
  
  def cmdDisp = cmd match
  { case Move(v) => Refs(Arrow.draw(state.posn, v))
    case CycleColour => Refs(state.drawNextColour)
    case _ => Refs()
  }
  
  def disp(): Unit =
  { reTop(Refs(StdButton.turn(state.turnNum + 1), status))
    mainRepaint(state.fillRect +: cmdDisp)
  }
  def newTurn(): Unit = { state = state.turn(cmd); cmd = NoMove; disp() }
  
  disp()
  
  topBar.mouseUp = (v, b , s) => s match
  { case List(Turn) => NoMove
    case _ => 
  }
  
  mainPanel.mouseUp = (v, b, s) => b match 
  { case RightButton => {cmd = Move(v); disp() }
    case LeftButton => { cmd = CycleColour; disp() }
    case MiddleButton => newTurn()
  }
}
