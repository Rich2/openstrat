/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** Lesson D4 Settings. */
case class LessonD5(canv: CanvasPlatform) extends CanvasNoPanels("Lesson D5")
{  
  val s1 = Ints(10, 9, 8, 7)
  val s2 = Vec2s(4 vv 3, 2.1 vv 0.7, 500 vv -100, Vec2Z)
  
  //val ss = Sett("Arr", s1).ap("Ls", s2).str
  //val c1 = ss.findIntArray
 
  //val cc = TextGraphic.lines(Arr(c1).map(_.toString), lineSpacing = 1.5, posn = -250 vv -150, align = LeftAlign)
  
  
  //repaintOld(SText(200, ss) +: cc)
  
}