/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pgui._, Colour._, BaseLine._//{Alphabetic, Bottom, Middle, Top}

case class LsA6(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A6")
{
  //We can create reusable values. ptStart is a dimensional vector. It has an x position (left-right and a y postion (up-down)
  val ptStart: Pt2 = -100 pp 50
  //Lets create another value and call it ptEnd. This is another Vec2, the compiler infers this, although we didn't state it directly.
  val ptEnd = 500 pp 300
  
  //However if you uncomment the line below you will get a compile error. You have told the compiler you are expecting a Vec2 but given it a number.
  //val badPoint: Vec2 = 100

  val arr = RArr(
    Bezier(ptStart, 200 pp 350, 0 pp 70, ptEnd).draw(Blue, 2),
    Bezier(ptStart, 200 pp 350, 100 pp 270, ptEnd).draw(Gray, 2),
    Bezier(ptStart, 100 pp 350, 100 pp 270, ptEnd).draw(Violet, 2),
    Bezier(ptStart, 0 pp 350, 100 pp 270, ptEnd).draw(DarkRed, 2),

    //Vec2Z is a predefined value for Vec2 where x is 0 and y is 0.
    TextOutline("Text in outline at centre screen", 40, Pt2Z, Orange, 1),
    TextOutline("More outline text, at x is 0, y is - 200", 60, 0 pp -200, Red, 2),
    TextOutline("Text with its baseline = top", 40, 100 pp 50, Blue, 1, LeftAlign, BaseLine.Top),

    // The following commands take variable numbers of parameters. You can add / remove parameters to see the effect
    Polygon(-300 pp 200, -300 pp 300, -250 pp 300).fill(Orange),
    Polygon(-250 pp 300, -200 pp 325, -150 pp 300, -275 pp 200).draw(Blue, 2),

    TextGraphic("default baseline", 18, 0 pp 150, Black, RightAlign),
    TextGraphic("top baseline", 18, 0 pp 150, Black, LeftAlign, Top),
    TextGraphic("middle", 18, 100 pp 150, Black, LeftAlign, Middle),
    TextGraphic("bottom", 18, 150 pp 150, Black, LeftAlign, Bottom),
    TextGraphic("alphabetic = ", 18, -230 pp 150, Black, LeftAlign, Alphabetic),
  )

  //So lets use those values above
  repaint(arr)
}