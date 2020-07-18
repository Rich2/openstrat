package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1")
{
  val sq = Square(100, -100, 200, 0.degs)
  debvar(sq.cen)
  val sq2 = Square(100, -100, 100, 0.degs)
  val sq3 = Square(100, -200, 100, 20.degs)
  debvar(sq)
  repaints(
    Circle(70).fill(Orange),
    Triangle.fill(-100 vv 0, 0 vv -200, -300 vv -400, Violet),
    Rectangle(200, 100, 100 vv 50).fill(Green),
    sq.fill(Red),
    sq2.fill(Pink),
    sq3.fill(Colour.DarkMagenta),
    TextGraphic("s1", 24, -100 vv 200)
  )
}

 