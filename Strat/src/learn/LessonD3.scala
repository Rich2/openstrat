/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** D Series lessons deal with persistence */
case class LessonD3(canv: CanvasPlatform) extends CanvasSimple("Lesson D3")
{
  val r1 = 5.str
  val r2 = 5.strTyped.toString
  val str = """Seq[Int](4; 5; 6)"""
  val r3 = str.findType[Seq[Int]].toString//The default constructor for a Seq is List
  val r4 = str.findType[List[Int]].toString
  val r5 = str.findType[Vector[Int]].toString
  val a6 = str.findType[Array[Int]]
  val r6 = a6.toString//toString method on Array not very helpful
  
  val strs = List(r1, r2, r3, r4, r5, r6).map(_.toString)
  repaint(TextGraphic.lines(strs))
  
}