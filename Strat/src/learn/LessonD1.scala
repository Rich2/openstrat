/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** D Series lessons deal with persistence */
case class LessonD1(canv: CanvasPlatform) extends CanvasSimple("Lesson D1")
{  
  val r1 = 5.str
  
  val r2 = 2.2.str
  
  val r3 = true.str//OK you're probably not noticing much advantage over toString yet except its shorter
  
  val v1 = Vec2(2.3, -9.8)
  val r4 = v1.str
  
  val v2: Vec2 = 4.6 vv 78.4
  val l1 = Line2(v1, v2)
  val r5 = l1.str//So note how there is a semicolon between the two points but a comma between the x and y values of each point.
  
  val v3 = v1.addX(50)
  val v4 = v1.subX(300)
  val v5 = 4.4 vv 5.5
  val v6 = v5.addY(100)
  //So in this longer example, the semicolons and commas become more useful. You can't do this with toString
  val r6 = List(v1, v2, v3, v4, v5, v6).str
  
  val strs = List(r1, r2, r3, r4, r5, r6)
  repaint(TextGraphic.lines(strs, lineSpacing = 1.5, posn = -250 vv 0, align = TextLeft))  
}