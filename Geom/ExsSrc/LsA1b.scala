/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._, Colour._

case class LsA1b(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1b")
{
  val cs = RArr(
    Circle(200).draw(lineColour = SeaGreen),
    Circle(140, 50, 80).draw(4, Orange),
    Circle(160, 300, 0).fillRadial(Green, Red),
    Circle(160, -250, 150).fillDraw(Turquoise, Black, 3),
    Circle(80, 0, -220).fillDraw(DarkGoldenRod, Violet, 12),
  )

  repaint(cs)
}
