/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._
import geom._
import ostrat.pgui.{CanvasNoPanels, CanvasPlatform}
import pWeb._

trait LessonPage
{ def page: HtmlPage
}

trait GraphicsA extends LessonPage
{ def title: String

  def canv: CanvasPlatform => Any
  def bodyStr: String

  override def page: HtmlPage = HtmlPage(head, HtmlBody(HtmlH1(title), HtmlP(bodyStr)))
  def head: HtmlHead = HtmlHead(RArr(HtmlTitle(title)))
}

trait GraphicsAE extends GraphicsA
{
  def output: GraphicElems

  def canv: CanvasPlatform => Any = new Canv(_, output)

  class Canv(val canv: CanvasPlatform, frame: GraphicElems) extends CanvasNoPanels(title)
  { repaint(frame)
  }
}