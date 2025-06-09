/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsEllipses2 extends LessonGraphics
{ override def title: String = "Ellipses 2"
  override def bodyStr: String = "Ellipses 2"
  override def canv: CanvasPlatform => Any = LsEllipses(_)
}

/** Ellipses 2 Lesson. Unfinished. */
case class LsEllipses2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ val el1: Ellipse = Ellipse(200, 100, -300, 0)
  val el2 = Ellipse(200, 300, 200, 0)

  repaints(el1.fill(Red), el2.fill(Colour.BurlyWood))
}