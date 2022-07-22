/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pWeb._

trait LessonPage
{ def page: HtmlPage
}

trait GraphicsA extends LessonPage
{
  def title: String
  def output: GraphicElems


  //def pair: (CanvasPlatform => Any, String) = (apply(_), title)
}
