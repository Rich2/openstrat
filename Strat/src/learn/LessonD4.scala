/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** Lesson D4 Settings. */
case class LessonD4(canv: CanvasPlatform) extends CanvasSimple("Lesson D4")
{
  val t1 = 5.str  
  val t2 = 2.2.str  
  val t3 = true.str//OK you're probably not noticing much advantage over toString yet except its shorter
  
  val v1 = Vec2(2.3, -9.8)
  val t4 = v1.str
  
  val v2: Vec2 = 4.6 vv 78.4
  
  val v3 = v1.addX(50)
  val v4 = v1.subX(300)
  val v5 = 4.4 vv 5.5
  val v6 = v5.addY(100)
  //So in this longer example, the semicolons and commas become more useful. You can't do this with toString
  val r6 = List(v1, v2, v3, v4, v5, v6).str
  
  repaints(SText(200, r6))
  
}



