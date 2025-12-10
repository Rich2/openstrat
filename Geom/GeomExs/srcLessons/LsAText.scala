/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat.*, geom.*, Colour.*, BaseLine.*

object LsAText extends LessonStatic
{ override def title: String = "Text"
  override def bodyStr: String = "Text"

    //We can create reusable values. ptStart is a dimensional vector. It has an x position (left-right and a y postion (up-down)
    val ptStart: Pt2 = Pt2(-100, 50)
    //Lets create another value and call it ptEnd. This is another Vec2, the compiler infers this, although we didn't state it directly.
    val ptEnd = Pt2(500, 300)

    //However if you uncomment the line below you will get a compile error. You have told the compiler you are expecting a Vec2 but given it a number.
    //val badPoint: Vec2 = 100

  override def output: GraphicElems = RArr(
    Bezier(ptStart, Pt2(200, 350), Pt2(0, 70), ptEnd).draw(2, Blue),
    Bezier(ptStart, Pt2(200, 350), Pt2(100, 270), ptEnd).draw(2, Gray),
    Bezier(ptStart, Pt2(100, 350), Pt2(100, 270), ptEnd).draw(2, Violet),
    Bezier(ptStart, Pt2(0, 350), Pt2(100, 270), ptEnd).draw(2, DarkRed),

    //Vec2Z is a predefined value for Vec2 where x is 0 and y is 0.
    TextOutline("Text in outline at centre screen", 40, Origin2, Orange, 1),
    TextOutline.xy("More outline text, at x is 0, y is - 200", 60, 0, -200, Red, 2),
    TextOutline.xy("Text with its baseline = top", 40, 100, 50, Blue, 1, LeftAlign, BaseLine.Top),

    // The following commands take variable numbers of parameters. You can add / remove parameters to see the effect
    Polygon.dbls(-300,200, -300,300, -250,300).fill(Orange),
    Polygon.dbls(-250,300, -200,325, -150,300, -275,200).draw(2, Blue),

    TextFixed.xy("default baseline", 18, 0, 150, Black, RightAlign),
    TextFixed.xy("top baseline", 18, 0, 150, Black, LeftAlign, Top),
    TextFixed.xy("middle", 18, 100, 150, Black, LeftAlign, Middle),
    TextFixed.xy("bottom", 18, 150, 150, Black, LeftAlign, Bottom),
    TextFixed.xy("alphabetic = ", 18, -230, 150, Black, LeftAlign, Alphabetic),
  )   
}