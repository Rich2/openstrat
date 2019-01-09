/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** E Series lessons deal with games. */
case class LessonE1(canv: CanvasPlatform) extends CmdBarGui("Lesson E1")
{
  import e1._
  var state = Scen(0 vv 0, Red)
  var cmd: Option[TurnCmd] = None
  var statusText = "Left click to set action to Move. Middle or right click to set action to CycleColour."  
  def rect = Rectangle.curvedCorners(80, 50, 15, state.posn)
  
  def cmdDisp = cmd match
  {
    case Some(Move(v)) => Arrow.draw(state.posn, v, zOrder = -1) :: Nil
    case Some(CycleColour) => rect.draw(4, state.nextColour) :: Nil
    case _ => Nil
  }
  reTop(List(StdButton.turn, status))
  def disp() = mainPanel.repaint(List(rect.fill(state.colour)) ::: cmdDisp)
  disp()
  
  mainPanel.mouseUp = (v, b, s) => b match 
  {
    case LeftButton => {cmd = Some(Move(v)); disp() }
    case _ => { cmd = Some(CycleColour); disp() }   
  }
}
package e1
{
  case class Scen(posn: Vec2, colour: Colour)
  {
    /** Move to a new posn if no greater than 150 pixel distant */
    def turn(cmd: TurnCmd): Scen = cmd match
    {
      case Move(toPosn) =>
      { val diff = toPosn  - posn
        val len = diff.magnitude
        val max = 150
        val newPosn = ife(len > max, posn + toPosn * max / len, toPosn)
        Scen(newPosn, colour)
      }   
      case CycleColour => Scen(posn, nextColour)
    
    }
    def nextColour: Colour = colour.nextFromRainbow
  }
  
  sealed trait TurnCmd
  case object CycleColour extends TurnCmd
  case class Move(toPosn: Vec2) extends TurnCmd
}



