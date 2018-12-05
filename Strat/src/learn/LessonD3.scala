/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** D Series lessons deal with persistence */
case class LessonD3(canv: CanvasPlatform) extends CanvasSimple("Lesson D3")
{
  val arr = Array(4, 5, 6)
  val str = arr.str
  val r1 = str.findType[Seq[Int]]//The default constructor for a Seq is List
  val r2 = str.findType[List[Int]]
  val r3 = str.findType[Vector[Int]]
  val a4 = str.findType[Array[Int]]
  val r4 = a4//toString method on Array not very helpful
  val r5 = a4.map(_(1))
  val r6: EMon[Int] = a4.map[Int](arr => arr(2))//This is the long explicit result.
  
  val strs = List(r1, r2, r3, r4, r5)map(_.toString)
  repaint(TextGraphic.lines(strs, lineSpacing = 1.5, posn = -250 vv 0, align = TextLeft))
  
}