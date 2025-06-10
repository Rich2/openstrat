/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsEllipses extends LessonGraphics
{ override def title: String = "Ellipses"
  override def bodyStr: String = "Ellipses"
  override def canv: CanvasPlatform => Any = LsEllipses(_)
}

/** Ellipse. Unfinished. */
case class LsEllipses(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ val el1: Ellipse = Ellipse(400, 250, -300, 0)
  val el2 = Ellipse(200, 300, 450, 0)

  val cen1: RArr[GraphicSvgElem] = el1.cen.textArrow("cen")
  val stuff = RArr(el1.draw(), el2.fillDraw(Colour.BurlyWood)) +% el1.axes.draw() +% el2.axes.draw() ++ el1.textArrows ++ el2.textArrows

  repaint(stuff)
}