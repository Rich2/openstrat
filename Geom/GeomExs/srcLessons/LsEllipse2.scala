/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsEllipses2 extends LessonGraphics
{ override def title: String = "Ellipses 2"
  override def bodyStr: String = "Ellipse 2"
  override def canv: CanvasPlatform => Any = LsEllipses2(_)
}

/** Ellipses 2 Lesson. Unfinished. */
case class LsEllipses2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ val el0 = Ellipse(200, 125, -20.degsVec, -300, 200)
  val el1: Ellipse = Ellipse(500, 250, 45.degsVec, 200, 0)

  val stuff = RArr(el0.fill(Red), el1.draw()) ++ el0.textArrows(Red) ++ el1.textArrows()
  repaint(stuff)
}