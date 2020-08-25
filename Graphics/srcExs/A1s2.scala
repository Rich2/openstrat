/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, Colour._, geom._

object A1s2 extends App
{
  val cfs = Arr(Circle(100, 0, 0).fillOld(SeaGreen),
  Circle(70, 50, 80).fillOld(Orange),
  Circle(80, 300, 0).fillOld(Red),
  Circle(80, -250, 150).fillOld(Colour.LemonLime),
  Circle(40, 0, -220).fillOld(Colour.DarkGoldenRod)
  )
  println(cfs.svgList)
}
