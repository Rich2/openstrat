/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._

case class HelloWorld(canv: CanvasPlatform) extends CanvasNoPanels("Hello World")
{ repaints(TextGraphic("Hello World!"))
}