/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** D Series lessons deal with persistence */
case class LessonD2(canv: CanvasPlatform) extends CanvasSimple("Lesson D2")
{
  val r1 = 5.str
  val r2 = 5.strTyped
  val str = """Seq[Int](4; 5; 6)"""
  val r3 = str.findType[Seq[Int]]//The default constructor for a Seq is List
  val r4 = str.findType[List[Int]]
  val r5 = str.findType[Vector[Int]]
  val r6 = str.findType[Array[Int]]
  
  val strs = List(r1, r2, r3, r4, r5, r6).map(_.toString)
  repaint(TextGraphic.lines(strs))
  
}