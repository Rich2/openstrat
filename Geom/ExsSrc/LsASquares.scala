/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._

/** Lesson introducing Squares. */
object LsASquares extends GraphicsAE
{
  override def title: String = "Squares"

  override def bodyStr: String = "Squares"

  val s1 = Sqlign(width = 150, cenX = 0, cenY = 0).fill(Red)
  val s2 = Sqlign(100, 200, 200).fill(Green)
  val s3 = Sqlign(100, 200, -200).fill(SkyBlue)
  val s4 = Sqlign(100, -200, -200).fill(Violet)
  val s5 = Sqlign(100, -200, 200).fill(Gold)
  val c1 = Circle(100, -200, 200).fill(Lime)
  val c2 = Circle(100, 255, 200).fill(Yellow)
  val c3 = Sqlign(50, 200, 200).fill(Salmon)

  override def output: GraphicElems = RArr(s1, s2, s3, s4, s5, c1, c2, c3)
}