/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

case class LsC3b(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C3")
{
  val el = Ellipselign(200, 100)
  val e1 = el.slateXY(-250, 150).fillActive(Green, "green")
  val e2 = el.slateXY(250, 150).fillActive(Yellow, "yellow")

  val rList: GraphicElems = RArr(e1, e2)
  val textPosn = 0 pp 0
  val startText = TextFixed("Please click on the screen.", 28, textPosn)
  repaint(rList +% startText)
  
  //Note we are ignoring the button here
  mouseUp = (button, selectedList, posn) =>
    {
      val newText = selectedList match
      { case RArrHead(h) => TextFixed("You hit a" -- h.toString -- "ellipse at " + posn.str, 28, textPosn)
        case _ => { deb(selectedList.toString()); TextFixed("You missed the ellipses.\n" + posn.str, 28, textPosn) }
      }  
      repaint(rList +% newText)
  }
}