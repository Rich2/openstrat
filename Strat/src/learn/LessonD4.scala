/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._

/** Under construction */
case class LessonD4(canv: CanvasPlatform) extends CanvasSimple("Lesson D4")
{  
  val r1 = Rval(5) - 2.1 - false - "Hello World!" - (2.3 vv -43.8) - Array(4, 5, 6)
  val s1 = r1.str
  val s2 = Sett('Age, 5).ap('Average, -2.1).ap('Open, false).ap('Greeting, "Hello World!"). ap('Posn, 2.3 vv -43.8).
  ap('Scores, Array(4, 5, 6)).str
 
  val c1 = s2.findSett[Boolean]('Open)
  val c2 = s2.findBooleanSett('Guilty)//Just a convenince method for the general one above
  val c3 = s2.findIntSett('Posn)
  val c4 = s2.findVec2Sett('Posn)//Again as Vec2 is such a commonly used type, special methods have been created for your convenience
  val c5 = s2.findVec2SettElse('MyPosn, 45 vv 1.2)
  val c6 = s2.findVec2SettElse('Posn, 45 vv 1.2)//Gives the result from the string, but has guard if setting not found.
  
  val cc = TextGraphic.lines(List(c1, c2, c3, c4, c5, c6).map(_.toString), lineSpacing = 1.5, posn = -250 vv -150, align = TextLeft)
  
  def ft(y: Double, str: String)  = TextGraphic(str, 24, -250 vv y, align = TextLeft) 
  repaint(ft(300 , s1) :: ft(100, s2) :: cc)
  
}