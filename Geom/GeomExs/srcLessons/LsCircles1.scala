/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

object LsCircles1 extends LessonStatic
{ val title = "Circles 1"

  val output: RArr[CircleFill] = RArr(
    Circle(radius = 35, cenX = 50, cenY = 80).fill(fillfacet = Orange),
    Circle(radius = 40, cenX = 300, cenY = 0).fill(Red),
    Circle(40, -250, 150).fill(LemonLime),
    Circle(20, 0, -220).fill(DarkGoldenRod),
    Circle(50).fill(SeaGreen),
  )

  val bodyStr: String = """We're starting with circles because they are so simple. You just need to specify four values. The radius how big it is from the
  | centre to the edge in terms of pixels. The circle's position in terms of left to right. The circle's position in terms of up and down. The circle's colour.
  | You can change the values and see the effects on the screen.""".stripMargin
}