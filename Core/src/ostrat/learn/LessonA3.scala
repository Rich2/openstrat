/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA3(canv: CanvasPlatform) extends CanvasSimple("Lesson A3")
{
  val r = Rectangle(200, 100).fillSubj(None, Yellow)
  val r1 = r.slate(-400, 0)
  val r2 = r.slate(400 vv 0)
  val rList = List(r1, r2)
  val textPosn = 0 vv 200
  val startText = TextGraphic(textPosn, "Please click on the screen.", 28)
  repaint(rList :+ startText)
  
  mouseUp = (v, b, s) =>
    {
      val newText = s match
      {
        case h :: tail => TextGraphic(textPosn, "You hit a yellow rectangle at " + v.commaStr, 28)
        case _ => TextGraphic(textPosn, "You missed the yellow rectangles " + v.commaStr, 28)
      }  
      repaint(rList :+ newText)
  }
}