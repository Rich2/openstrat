/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.{CanvasNoPanels, CanvasPlatform }, pWeb.*

trait LessonPage
{ def page: HtmlPage
}

trait LessonGraphics extends LessonPage
{ def title: String

  def canv: CanvasPlatform => Any
  def bodyStr: String

  override def page: HtmlPage = HtmlPage(head, HtmlBody(HtmlH1(title), HtmlP(bodyStr)))
  def head: HtmlHead = HtmlHead(RArr(HtmlTitle(title)))
}

trait LessonStatic extends LessonGraphics
{
  def output: GraphicElems

  def canv: CanvasPlatform => Any = new Canv(_, output)

  class Canv(val canv: CanvasPlatform, frame: GraphicElems) extends CanvasNoPanels(title)
  { repaint(frame)
  }
}