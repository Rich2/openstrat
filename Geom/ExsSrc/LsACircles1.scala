/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._, pWeb._

object LsACircles1 extends GraphicsAE
{ val title = "Circles 1"

  val output: RArr[CircleFill] = RArr(
    Circle(100, 0, 0).fill(SeaGreen),
    Circle(70, 50, 80).fill(Orange),
    Circle(80, 300, 0).fill(Red),
    Circle(80, -250, 150).fill(LemonLime),
    Circle(40, 0, -220).fill(DarkGoldenRod),
  )

  val bodyStr: String =
  """This lesson covers Circles and Ellipses. As with the other lessons there will be a summary for those familiar with Scala and anumber of step
  |by step parts for those new to Scala, programming or geometry and graphics.""".stripMargin
}
