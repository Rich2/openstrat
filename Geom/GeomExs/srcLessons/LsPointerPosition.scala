/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsPointerPosition extends LessonGraphics
{ override def title: String = "Pointer position Lesson"
  override def bodyStr: String = """Shows the location and [[Pt2]] coordinates of the point on the screen the user has clicked."""
  override def canv: CanvasPlatform => Any = LsC2Canv(_)

  case class LsC2Canv(canv: CanvasPlatform) extends CanvasNoPanels("Lesson C2")
  { repaints(TextFixed.xy("Please click on the screen in different places.", 28, 0, 200, Green))
    setMouseSimple(pt => repaints(TextFixed("You clicked the screen at: " + pt.tellSemisNames(), 28, pt, Red)))
  }
}