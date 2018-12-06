/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** Under construction */
case class LessonD4(canv: CanvasPlatform) extends CanvasSimple("Lesson D4")
{
  val s1 = 5.str
  val s2 = 2.1.str
  val s3 = false.str
  val s4 = "Hello World!".str
  val s5 = Vec2(2.3, -43.8).str
  //List(5, 2.1, false, "Hello World!", Vec2(2.3, -43.8)).map(_.str)
    
  val strs = List(s1, s2, s3, s4, s5)map(_.toString)
  repaint(TextGraphic.lines(strs, lineSpacing = 1.5, posn = -250 vv 0, align = TextLeft))
  
}