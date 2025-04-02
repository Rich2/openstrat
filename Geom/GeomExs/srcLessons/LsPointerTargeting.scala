/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

object LsPointerTargeting extends LessonGraphics
{ override def title: String = "Pointer targeting Lesson"

  override def bodyStr: String = """Lesson C3. Pointer in object."""

  override def canv: CanvasPlatform => Any = LsC3Canv(_)

  case class LsC3Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C3")
  { val r: PolygonCompound = Rect(200, 100).fillActive(Yellow, None)
    val r1: PolygonCompound = r.slate(-300, 300)
    val r2: PolygonCompound = r.slate(300, 300)
    val r3: PolygonCompound = r.slate(300, -300)
    val r4: PolygonCompound = r.slate(-300, -300)
    val rList = RArr(r1, r2, r3, r4)
    val textPosn = Pt2Z
    val startText = TextFixed("Please click on the screen.", 28, textPosn)
    repaint(rList +% startText)

    //Note we are ignoring the button here
    mouseUp = (button, selectedList, posn) => {
      val newText = selectedList match {
        case RArrHead(h) => TextFixed("You hit a yellow rectangle at " + posn.str, 28, textPosn)
        case _ => {
          deb(selectedList.toString()); TextFixed("You missed the yellow rectangles.\n" + posn.str, 28, textPosn)
        }
      }
      repaint(rList +% newText)
    }
  }
}