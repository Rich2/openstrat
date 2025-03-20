/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

/** Not sure if this is still useful. */
object LsACirclesApp extends App
{
  val cfs = RArr(Circler(50, 0, 0).fill(SeaGreen),
  Circled(70, 50, 80).fill(Orange),
  Circled(80, 300, 0).fill(Red),
  Circled(80, -250, 150).fill(Colour.LemonLime),
  Circled(40, 0, -220).fill(Colour.DarkGoldenRod)
  )
}