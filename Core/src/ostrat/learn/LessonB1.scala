/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonB1(canv: CanvasPlatform) extends CanvasSimple("Lesson B1")
{
  repaints(
      TextGraphic(100 vv 200, "Hi, there!", 28, Red), //This test is centred 300 pixels up and 100 pixels to the right of centre  
      TextGraphic(0 vv 0, "This text is centred on the centre of the canvas. The point from which postions are measured.", 18, Blue),
      TextGraphic(100 vv -100, "Text centred at 100, -100", 25),
      //TextGraphic(100 vv -200, "This text can't be seen till you uncomment it and rebuild the programme.", 18, Violet)
      )  
}