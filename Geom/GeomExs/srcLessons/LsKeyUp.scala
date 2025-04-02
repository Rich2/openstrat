/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsKeyUp extends LessonGraphics
{ override def title: String = "Key up"
  override def bodyStr: String = """Key up."""

  override def canv: CanvasPlatform => Any = LsC6Canv(_)

  case class LsC6Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C6")
  { deb("Lesson C6")
    val startText = TextFixed.xy("Press a key.", 28, 0, 300)
    repaints(startText)

    canv.keyDown = (key: String) => {
      repaints(TextFixed("key down '" + key + "'", 28, Pt2Z, Blue))
    }
    canv.keyUp = (key: String) => {
      repaints(TextFixed.xy("key up '" + key + "'", 28, 0, 100, Green))
    }
  }
}