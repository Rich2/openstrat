/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._, pWeb._

/** More stuff with circles. */
object LsACircles2 extends LessonStatic
{
  override def title: String = "Circles 2"

  override def bodyStr: String = """"""

  val output = RArr(
    Circler(100).draw(lineColour = SeaGreen),
    Circler(70, 50, 80).draw(4, Orange),
    Circler(80, 300, 0).fillRadial(Green, Red),
    Circler(80, -250, 150).fillDraw(Turquoise, Black, 3),
    Circler(40, 0, -220).fillDraw(DarkGoldenRod, Violet, 12),
  )
}