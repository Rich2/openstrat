package learn
import ostrat._, geom._, pCanv._, pGrid._, Colour._

/** 1st lesson in a tile grid lesson series. */
case class LessonG1(canv: CanvasPlatform) extends CanvasSimple("Lesson G1")
{
  val f: FSSCood[Colour] = c => Red
  repaints(
    Triangle.fill(-100 vv 0, 0 vv -200, -300 vv -400, Violet),
    Rectangle(200, 100, 100 vv 50).fill(Green),
    Square.fill(100, Orange, 300 vv 0)
  )
}
