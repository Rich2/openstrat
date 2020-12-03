/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._

trait GraphicsA
{
  def title: String
  def output: GraphicElems
  def apply(canvIn: CanvasPlatform): Any = new CanvasNoPanels(title)
  { val canv: CanvasPlatform = canvIn
    repaint(output)
  }

  def pair: (CanvasPlatform => Any, String) = (apply(_), title)
}
