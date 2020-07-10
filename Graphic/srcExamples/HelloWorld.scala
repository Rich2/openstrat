package learn
import ostrat._, geom._, pCanv._

case class HelloWorld(canv: CanvasPlatform) extends CanvasNoPanels("Hello World")
{ repaints(TextGraphic("Hello World!"))
}