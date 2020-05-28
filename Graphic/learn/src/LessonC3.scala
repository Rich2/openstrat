/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonC3(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C3")
{
  val r = Rectangle(200, 100).parentFill(None, Yellow)
  val r1 = r.slate(-300, 300)
  val r2 = r.slate(300 vv 300)
  val r3 = r.slate(300 vv - 300)
  val r4 = r.slate(-300 vv - 300)
  val rList = Arr(r1, r2, r3, r4)
  val textPosn = 0 vv 0
  val startText = TextGraphic("Please click on the screen.", 28, textPosn)
  repaint(rList +- startText)
  
  //Note we are ignoring the button here
  mouseUp = (button, selectedList, posn) =>
    {
      val newText = selectedList match
      { case ::(h, _) => TextGraphic("You hit a yellow rectangle at " + posn.strCommaNames, 28, textPosn)
        case _ => TextGraphic("You missed the yellow rectangles.\n" + posn.strCommaNames, 28, textPosn)
      }  
      repaint(rList +- newText)
  }
}