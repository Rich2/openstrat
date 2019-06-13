/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** E Series lessons deal with games. E1 is a super simple single player turn game.  */
case class LessonE1(canv: CanvasPlatform) extends CmdBarGui("Lesson E1")
{
  import e1._
  var state: GState = GState.start
  var cmd: Option[TurnCmd] = None
  var statusText = "Left click to set action to Move. Middle or right click to set action to CycleColour."  
  
  def cmdDisp = cmd match
  {
    case Some(Move(v)) => Arr(Arrow.draw(state.posn, v, zOrder = -1))
    case Some(CycleColour) => Arr(state.drawNextColour)
    case _ => Arr()
  }
  
  def disp() =
  { reTop(Arr(StdButton.turn(state.turnNum + 1), status))
    mainPanel.repaint(state.fillRect +: cmdDisp)
  }
  
  disp()
  
  topBar.mouseUp = (v, b , s) => s match
  {
    case Arr(Turn) => { state = state.turn(cmd); cmd = None; disp() }
    case _ => 
  }
  
  mainPanel.mouseUp = (v, b, s) => b match 
  {
    case LeftButton => {cmd = Some(Move(v)); disp() }
    case _ => { cmd = Some(CycleColour); disp() }   
  }
}
