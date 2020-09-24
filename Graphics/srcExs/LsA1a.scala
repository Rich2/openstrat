/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

object LsA1a
{
  val arr = Arr(
    Circle(100, 0, 0).fill(SeaGreen),
    Circle(70, 50, 80).fill(Orange),
    Circle(80, 300, 0).fill(Red),
    Circle(80, -250, 150).fill(LemonLime),
    Circle(40, 0, -220).fill(DarkGoldenRod),
  )
}

case class LsA1a(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1a")
{ repaint(LsA1a.arr)
}