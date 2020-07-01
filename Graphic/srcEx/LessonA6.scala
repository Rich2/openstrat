/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._, Colour._, BaseLine._//{Alphabetic, Bottom, Middle, Top}

case class LessonA6(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A6")
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
      TextOutline("Text in outline at centre screen", 40, Vec2Z, Orange, 1),
      TextOutline("More outline text, at x is 0, y is - 200", 60, 0 vv -200, Red, 2),
      TextOutline("Text with its baseline = top", 40, 100 vv 50, Blue, 1, LeftAlign, BaseLine.Top),

      // The following commands take variable numbers of parameters. You can add / remove parameters to see the effect
      PolygonClass(-300 vv 200, -300 vv 300, -250 vv 300).fill(Orange),
      PolygonClass(-250 vv 300, -200 vv 325, -150 vv 300, -275 vv 200).draw(2, Blue),

    TextGraphic("default baseline", 18, 0 vv 150, Black, RightAlign),
    TextGraphic("top baseline", 18, 0 vv 150, Black, LeftAlign, Top),
    TextGraphic("middle", 18, 100 vv 150, Black, LeftAlign, Middle),
    TextGraphic("bottom", 18, 150 vv 150, Black, LeftAlign, Bottom),
    TextGraphic("alphabetic = ", 18, -230 vv 150, Black, LeftAlign, Alphabetic),

  )
}