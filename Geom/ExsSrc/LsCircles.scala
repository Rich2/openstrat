/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

/** Circles are simple. */
object LsCircles
{
  val arr = RArr(
    Circle(200, 0, 0).fill(SeaGreen),
    Circle(140, 50, 80).fill(Orange),
    Circle(160, 300, 0).fill(Red),
    Circle(160, -250, 150).fill(LemonLime),
    Circle(80, 0, -220).fill(DarkGoldenRod),
  )
}

case class LsCircles(canv: CanvasPlatform) extends CanvasNoPanels("Circles are simple")
{ repaint(LsCircles.arr)
}