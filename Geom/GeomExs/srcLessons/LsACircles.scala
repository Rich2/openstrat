/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsACircles extends LessonStatic
{ val title = "Coloured Circles"

  val output: RArr[CircleFill] = RArr(
    Circle(50, cenX = 0, cenY = 0).fill(fillFacet = SeaGreen),
    Circle.d(70, 50, 80).fill(Orange),
    Circle.d(80, 300, 0).fill(Red),
    Circle.d(80, -250, 150).fill(LemonLime),
    Circle.d(40, 0, -220).fill(DarkGoldenRod),
  )

  val bodyStr: String =
  """We're starting with circles because they are so simple. You just need to specify four values. The diameter how big is it in terms of pixels.
  | The circle's position in terms of left to right. The circle's position in terms of up and down. The circle's colour.""".stripMargin
}