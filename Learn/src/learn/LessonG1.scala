package learn
import ostrat._, geom._, pCanv._, pGrid._, Colour._

/** 1st lesson in a tile grid lesson series. */
case class LessonG1(canv: CanvasPlatform) extends CanvasSimple("Lesson G1")
{
  val f: FSSCood[Colour] = c => c.fEvenSum(Red, LightGreen)
  val ge = SSGridRange(8, 8)
  val a = ge.fDisp(f, 40)
  repaint(a

  )
}
