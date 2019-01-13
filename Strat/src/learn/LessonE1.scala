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
  def rect = Rectangle.curvedCorners(80, 50, 15, state.posn)
  
  def cmdDisp = cmd match
  {
    case Some(Move(v)) => Arrow.draw(state.posn, v, zOrder = -1) :: Nil
    case Some(CycleColour) => rect.draw(4, state.nextColour) :: Nil
    case _ => Nil
  }
  
  def disp() =
  { reTop(List(StdButton.turn(state.turnNum + 1), status))
    mainPanel.repaint(List(rect.fill(state.colour)) ::: cmdDisp)
  }
  
  disp()
  
  topBar.mouseUp = (v, b , s) => s match
  {
    case Turn :: Nil => { state = state.turn(cmd); cmd = None; disp() }
    case _ => 
  }
  
  mainPanel.mouseUp = (v, b, s) => b match 
  {
    case LeftButton => {cmd = Some(Move(v)); disp() }
    case _ => { cmd = Some(CycleColour); disp() }   
  }
}
package e1
{
  /** GameState. The game world state between turns. */
  case class GState(turnNum: Int, posn: Vec2, rainbowCycle: RainbowCycle)
  {
    /** Move to a new posn if no greater than 150 pixel distant */
    def turn(cmd: Option[TurnCmd]): GState = cmd match
    {
      case Some(Move(toPosn)) =>
      { val diff = toPosn  - posn
        val len = diff.magnitude
        val max = 150
        val newPosn = ife(len > max, posn + toPosn * max / len, toPosn)
        GState(turnNum + 1, newPosn, rainbowCycle)
      }   
      case Some(CycleColour) => GState(turnNum + 1, posn, rainbowCycle.next)
      case _ => copy(turnNum + 1)
    }
    def colour: Colour = rainbowCycle()
    def nextColour: Colour = rainbowCycle.nextValue
  }
  
  object GState
  {
    def start = GState(0, 0 vv 0, RainbowCycle.start)
  }
  
  sealed trait TurnCmd
  case object CycleColour extends TurnCmd
  case class Move(toPosn: Vec2) extends TurnCmd
}
