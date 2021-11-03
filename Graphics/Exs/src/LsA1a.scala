/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

object LsA1a
{
  val arr = Arr(
    Circle(200, 0, 0).fill(SeaGreen),
    Circle(140, 50, 80).fill(Orange),
    Circle(160, 300, 0).fill(Red),
    Circle(160, -250, 150).fill(LemonLime),
    Circle(80, 0, -220).fill(DarkGoldenRod),
  )
}

case class LsA1a(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1a")
{ repaint(LsA1a.arr)
}