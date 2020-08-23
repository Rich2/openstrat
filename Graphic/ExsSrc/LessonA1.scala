/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1")
{
  repaints(
    CircleDisplay(Circle(100, 0, 0), Arr(FillColour(SeaGreen)), Arr()),
    Circle(70, 50, 80).fill(Orange),
    Circle(80, 300, 0).fill(Red),
    Circle(80, -250, 150).fill(Colour.LemonLime),
    Circle(40, 0, -220).fill(Colour.DarkGoldenRod),
  )
}

 