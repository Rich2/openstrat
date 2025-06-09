/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, Colour.*

object LsEllipses extends LessonGraphics
{ override def title: String = "Ellipses"
  override def bodyStr: String = "Ellipses"
  override def canv: CanvasPlatform => Any = LsEllipses(_)
}

/** Lesson A3. Unfinished. */
case class LsEllipses(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{ val el1: Ellipse = Ellipse(400, 250, -300, 0)
  val el2 = Ellipse(200, 300, 450, 0)
  val p1: RArr[GraphicSvgElem] = el1.axesPt1.textArrow("axesPt1")
  val p2: RArr[GraphicSvgElem] = el1.axesPt2.textArrow("axesPt2")
  val p3: RArr[GraphicSvgElem] = el1.axesPt3.textArrow("axesPt3")
  val p4: RArr[GraphicSvgElem] = el1.axesPt4.textArrow("axesPt4")
  val cen1: RArr[GraphicSvgElem] = el1.cen.textArrow("centre")
  val stuff = RArr(el1.fill(Red), el2.fillDraw(Colour.BurlyWood)) ++ p1 ++ p2 ++ p3 ++ p4 ++ cen1

  repaint(stuff)
}