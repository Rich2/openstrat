package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1")
{
  repaints(
    Triangle.fill(-100 vv 0, 0 vv -200, -300 vv -400, Violet),
    Rectangle(200, 100, 100 vv 50).fill(Green),
    //Square.fill(Orange).scaleSlateX(100, 300),
    TextGraphic("Hello World!"),
  )
}

 