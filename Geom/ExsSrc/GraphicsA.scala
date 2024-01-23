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
{
  def title: String
  def output: GraphicElems

  //def pair: (CanvasPlatform => Any, String) = (apply(_), title)
}

trait GraphicsAE extends GraphicsA
{
  def output: GraphicElems

  case class Canv(canv: CanvasPlatform) extends CanvasNoPanels(title)
  {
    repaint(output)
  }
}