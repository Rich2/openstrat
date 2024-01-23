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
  def output: GraphicElems
}

trait GraphicsAE extends GraphicsA
{
  def output: GraphicElems

  def canv = new Canv(_, output)

  class Canv(val canv: CanvasPlatform, frame: GraphicElems) extends CanvasNoPanels(title)
  { repaint(frame)
  }
}