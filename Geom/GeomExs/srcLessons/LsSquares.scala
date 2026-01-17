/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, geom.*, Colour.*

/** Lesson introducing Squares. */
object LsSquares extends LessonStatic
{
  override def title: String = "Squares"

  override def bodyStr: String = "Squares"

  val s1 = Sqlign(width = 150, cenX = 0, cenY = 0).fill(Red)
  val s2 = Sqlign(100, 200, 200).fill(Green)
  val s3 = Sqlign(100, 200, -200).fill(SkyBlue)
  val s4 = Sqlign(100, -200, -200).fill(Violet)
  val s5 = Sqlign(100, -200, 200).fill(Gold)
  val c1 = Circle(50, -200, 200).fill(Lime)
  val c2 = Circle(50, 255, 200).fill(Yellow)
  val c3 = Sqlign(50, 200, 200).fill(Salmon)

  val sq1 = Sqlign(150, 200, 0)
  val com1 = SqlignCompound(sq1, DrawFacet())(sq1.diags.draw())

  override def output: GraphicElems = RArr(s1, s2, s3, s4, s5, c1, c2, c3, com1)
}