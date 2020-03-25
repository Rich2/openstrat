/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** E Series lessons deal with games. E1 is a super simple single player turn game.  */
case class LessonE1(canv: CanvasPlatform) extends CmdBarGui("Lesson E1")
{
  import e1._
  var state: GameState = GameState.start
  var cmd: Option[TurnCmd] = None
  var statusText = "Right click to set action to Move. Left or Middle click to set action to CycleColour."
  
  def cmdDisp = cmd match
  { case Some(Move(v)) => Refs(Arrow.draw(state.posn, v))
    case Some(CycleColour) => Refs(state.drawNextColour)
    case _ => Refs()
  }
  
  def disp(): Unit =
  { reTop(Refs(StdButton.turn(state.turnNum + 1), status))
    mainRepaint(state.fillRect +: cmdDisp)
  }
  
  disp()
  
  topBar.mouseUp = (v, b , s) => s match
  { case List(Turn) => { state = state.turn(cmd); cmd = None; disp() }
    case _ => 
  }
  
  mainPanel.mouseUp = (v, b, s) => b match 
  { case RightButton => {cmd = Some(Move(v)); disp() }
    case _ => { cmd = Some(CycleColour); disp() }   
  }
}
