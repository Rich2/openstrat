/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** D Series lessons deal with persistence */
case class LessonD2(canv: CanvasPlatform) extends CanvasSimple("Lesson D2")
{
  val str = """2.0; "Hello"; 7; Vec2(2.3; 3.2); "Goodbye" """
  val r1 = str.findInt
  val r2 = str.findType[Int]//Does the same as r1
  val r3 = str.findType[String]
  val r4 = str.findTypeIndex[String](0)//Indexs start at 0
  val r5 = str.findTypeIndex[String](1)
  val r6 = str.findTypeIndex[String](2)//Because Indexs start at 0. There is no element 2 of type String.
  val r7 = str.findType[Vec2]
  val strs = List(r1, r2, r3, r4, r5, r6, r7).map(_.toString)
  repaint(TextGraphic.lines(strs, lineSpacing = 1.5, posn = -250 vv 0, align = TextLeft))
  
}