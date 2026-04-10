/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, pgui.*, pWeb.*

trait LessonGraphics
{ def title: String
  
  def canv: CanvasPlatform => Any
  def bodyStr: String

  def page: PageHtml = PageHtml(head, BodyHtml(HtmlH1(title), HtmlP(bodyStr)))
  def head: HeadHtml = HeadHtml(RArr(HtmlTitle(title)))
}

trait LessonStatic extends LessonGraphics
{
  def output: GraphicElems

  def canv: CanvasPlatform => Any = new Canv(_, output)

  class Canv(val canv: CanvasPlatform, frame: GraphicElems) extends CanvasNoPanels(title)
  { repaint(frame)
  }
}