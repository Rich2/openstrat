/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonB2(canv: CanvasPlatform) extends CanvasSimple("Lesson B2")
{
  timedRepaint1{e =>
    val e2 = e % 5000
    Rectangle(200, 100).slateX(e2 / 4 - 600).fill(Red)
  }
}