/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

case class LsB3(canv: CanvasPlatform) extends CanvasNoPanels("Lesson B3")
{
  /* This again uses the next simplest timer method. This requires a list of objects. */
  timedRepaint{e =>
    val e2 = e % 5000
    val e3 = 5000 - e2
    RArr(
      Rect(200, 100).slateX(e3 / 4 - 600).fill(Colour.fromInts(0, 255 * e2 / 5000, 255 * e3 / 5000)),
      Rect(200, 100).slateX(e2 / 4 - 600).fill(Colour.fromInts(255* e3 / 5000, 255 * e2 /5000, 0 ,  128))
    )
  }
}
