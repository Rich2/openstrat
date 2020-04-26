package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1")
{
  repaints(
    TextGraphic("Hello World!")
  )
}

 