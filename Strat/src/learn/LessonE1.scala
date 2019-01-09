/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** E Series lessons deal with games. */
case class LessonE1(canv: CanvasPlatform) extends CanvasSimple("Lesson E1")
{
  import e1._
  var state = Scen(0 vv 0, Red)
  var cmd: Option[TurnCmd] = null
  val tg = TextGraphic("Left click within 200 pixels to move. Middle or right click to cycle colour.", 24, 0 vv 300)
  def rect = Rectangle.curvedCorners(80, 50, 15, state.posn)
  def cmdDisp = cmd match
  {
    case Some(Move(v)) => Arrow.draw(state.posn, v, zOrder = -1) :: Nil
    case Some(CycleColour) => rect.draw(4, state.nextColour) :: Nil
    case _ => Nil
  }
  def disp() = repaint(List(tg, rect.fill(state.colour)) ::: cmdDisp)
  disp()
  
  mouseUp = (v, b, s) => b match 
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
      {
        val len = (toPosn  - posn).magnitude 
        val newPosn = ife(len > 150, posn, toPosn)
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



