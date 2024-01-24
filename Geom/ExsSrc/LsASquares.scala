/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

object LsASquares extends GraphicsAE
{
  override def title: String = "Squares"

  override def bodyStr: String = "Squares"

  val s1 = Sqlign(150).fill(Red)


  override def output: GraphicElems = RArr(s1)
}