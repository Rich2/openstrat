/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA3(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A3")
{
  val x1 = 100
  val y1 = 200
  val circles = Arr(Circle(100).fillOld(Green), Circle(100, 0, 200).fillOld(Violet), Circle(100, 200, 0).fillOld(SandyBrown),
    Circle(100, 0, -200).fillOld(Turquoise))
  val crosses =  Vec2s(0 vv 0, -100 vv 0, 100 vv 0, 0 vv 100).flatMap(v => Cross(1, v))
  repaint(circles ++ crosses)
}


 