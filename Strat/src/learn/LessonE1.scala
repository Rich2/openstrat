/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** E Series lessons deal with games. */
case class LessonE1(canv: CanvasPlatform) extends CanvasSimple("Lesson E1")
{  
  var state = ScenE1(0 vv 0, Red)
  val tg = TextGraphic("Left click within 200 pixels to move. Middle or right click to cycle colour.", 24, 0 vv 300)
  def disp() = repaint(List(tg, Rectangle.curvedCorners(80, 50, 15, state.posn).fill(state.colour)))
  disp()
  
  mouseUp = (v, b, s) => b match 
  {
    case LeftButton => {state = state.move(v); disp() }
    case _ => { state = state.cycleColour; disp() }   
  }
}

case class ScenE1(posn: Vec2, colour: Colour)
{
  /** Move to a new posn if no greater than 150 pixel distant */
  def move(toPosn: Vec2): ScenE1 =
  {
    val len = (toPosn  - posn).magnitude 
    val newPosn = ife(len > 150, posn, toPosn)
    ScenE1(newPosn, colour)
  }
  
  def cycleColour: ScenE1 = ScenE1(posn, colour.nextFromRainbow)
}

