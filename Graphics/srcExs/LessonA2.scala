/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA2(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A2")
{
  val sq2 = SquareGen(100, -100, 100, 0.degs)
  val sq3 = SquareGen(100, -200, 100, 20.degs)

  repaints(
    Triangle.fill(-100 vv 0, 0 vv -200, -300 vv -400, Violet),
    Rect(200, 100, 100 vv 50).fillOld(Green),
  )
}