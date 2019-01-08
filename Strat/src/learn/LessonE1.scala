/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

/** E Series lessons deal with games. */
case class LessonE1(canv: CanvasPlatform) extends CanvasSimple("Lesson E1")
{  
  var state = ScenE1(0 vv 0, Vec2s())
  def disp = List(Rectangle.curvedCorners(80, 50, 15, state.posn).fill(Red), state.dropSeq.draw(2, Green))
  repaint(disp)  
}

case class ScenE1(posn: Vec2, dropSeq: Vec2s)
{
  /** Move to a new posn if no greater than 100 pixel distant */
  def move(toPosn: Vec2): ScenE1 =
  {
    val len = (toPosn  - posn).magnitude 
    val newPosn = ife(len > 100, posn, toPosn)
    ScenE1(newPosn, dropSeq)
  }
  
  def drop: ScenE1 = ScenE1(posn, dropSeq :+ posn)
}

