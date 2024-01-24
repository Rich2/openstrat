/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsACircles1 extends GraphicsAE
{ val title = "Coloured Circles"

  val output: RArr[CircleFill] = RArr(
    Circle(diameter = 100, cenX = 0, cenY = 0).fill(fillColour = SeaGreen),
    Circle(70, 50, 80).fill(Orange),
    Circle(80, 300, 0).fill(Red),
    Circle(80, -250, 150).fill(LemonLime),
    Circle(40, 0, -220).fill(DarkGoldenRod),
  )

  val bodyStr: String =
  """We're starting with circles because they are so simple. You just need to specify four values. The diameter how big is it in terms of pixels.
  | The circle's position in terms of left to right. The circle's position in terms of up and down. The circle's colour.""".stripMargin
}
