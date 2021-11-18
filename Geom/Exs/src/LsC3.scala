/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

case class LsC3(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C3")
{
  val r = Rect(200, 100).fillActive(Yellow,None)
  val r1 = r.slateXY(-300, 300)
  val r2 = r.slate(300 pp 300)
  val r3 = r.slate(300 pp - 300)
  val r4 = r.slate(-300 pp - 300)
  val rList = Arr(r1, r2, r3, r4)
  val textPosn = 0 pp 0
  val startText = TextGraphic("Please click on the screen.", 28, textPosn)
  repaint(rList +% startText)
  
  //Note we are ignoring the button here
  mouseUp = (button, selectedList, posn) =>
    {
      val newText = selectedList match
      { case ArrHead(h) => TextGraphic("You hit a yellow rectangle at " + posn.str, 28, textPosn)
        case _ =>
          { deb(selectedList.toString())
            TextGraphic("You missed the yellow rectangles.\n" + posn.str, 28, textPosn)
          }
      }  
      repaint(rList +% newText)
  }
}