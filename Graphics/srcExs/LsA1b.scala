/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LsA1b(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1b")
{
  val cs = Arr(
    Circle(200).draw(SeaGreen, 2),
    Circle(140, 50, 80).draw(Orange, 4),
    Circle(160, 300, 0).fillRadial(Green, Red),
    Circle(160, -250, 150).fillDraw(Turquoise, 3, Black),
    Circle(80, 0, -220).fillDraw(DarkGoldenRod, 12, Violet),
  )

  repaint(cs)
}
