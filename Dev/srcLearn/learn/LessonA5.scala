/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonA5(canv: CanvasPlatform) extends CanvasSimple("Lesson A5")
{
  //We can create reusable values. ptStart is a dimensional vector. It has an x position (left-right and a y postion (up-down)
  val ptStart: Vec2 = -100 vv 50
  //Lets create another value and call it ptEnd. This is another Vec2, the compiler infers this, although we didn't state it directly.
  val ptEnd = 500 vv 300
  
  //However if you uncomment the line below you will get a compile error. You have told the compiler you are expecting a Vec2 but given it a number.
  //val badPoint: Vec2 = 100
  
  repaints(//So lets use those values above
      BezierDraw(ptStart, 200 vv 350, 0 vv 70, ptEnd, 2, Blue),
      BezierDraw(ptStart, 200 vv 350, 100 vv 270, ptEnd, 2, Gray),
      BezierDraw(ptStart, 100 vv 350, 100 vv 270, ptEnd, 2, Violet),
      BezierDraw(ptStart, 0 vv 350, 100 vv 270, ptEnd, 2, DarkRed),
  
      //Vec2Z is a predefined value for Vec2 where x is 0 and y is 0.
      TextOutline(Vec2Z, "Text in outline at centre screen", 40, Orange, 1),
      TextOutline(0 vv -200, "More text in outline, at x is 0, y is - 200", 60, Red, 2),
  
      // The following commands take variable numbers of parameters. You can add / remove parameters to see the effect
      Polygon(-300 vv 200, -300 vv 300, -250 vv 300).fill(Orange),
      Polygon(-250 vv 300, -200 vv 325, -150 vv 300, -275 vv 200).draw(2, Blue), 
      )  
}