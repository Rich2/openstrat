/* Copyright 2018-26 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

/** Not sure if this is still useful. */
@main def LsACirclesApp =
{ val cfs = RArr(Circle(50, 0, 0).fill(SeaGreen),
  Circle.d(70, 50, 80).fill(Orange),
  Circle.d(80, 300, 0).fill(Red),
  Circle.d(80, -250, 150).fill(Colour.LemonLime),
  Circle.d(40, 0, -220).fill(Colour.DarkGoldenRod)
  )
}