/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

/** More stuff with circles. */
object LsCircles2 extends LessonStatic
{
  override def title: String = "Circles 2"

  override def bodyStr: String = """"""

  val output = RArr(
    Circle(100).draw(lineColour = SeaGreen),
    Circle(70, 50, 80).draw(4, Orange),
    Circle(80, 300, 0).fillRadial(Green, Red),
    Circle(80, -250, 150).fillDraw(Turquoise, Black, 3),
    Circle(40, 0, -220).fillDraw(DarkGoldenRod, Violet, 12),
  )
}