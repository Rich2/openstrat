/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** D Series lessons deal with persistence */
case class LessonD1(canv: CanvasPlatform) extends CanvasSimple("Lesson D1")
{
  val str = """5; "Hello"; 7; Vec2(2.3; 3.2); "Goodbye" """
  val r1 = str.findType[Int]
  val r2 = str.findTypeFirst[Int]
  val r3 = str.findType[String]
  val r4 = str.findTypeIndex[String](2)
  val r5 = str.findType[Vec2]
  val strs = List(r1, r2, r3, r4, r5).map(_.toString)
  repaint(TextGraphic.lines(strs))
  
}