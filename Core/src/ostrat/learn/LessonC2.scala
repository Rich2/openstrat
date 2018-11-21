/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._, Colour._

case class LessonC2(canv: CanvasPlatform) extends CanvasSimple("Lesson C2")
{
  repaints(TextGraphic(0 vv 200, "Please click on the screen in different places.", 28, Green))  
  setMouseSimple(v => repaints(TextGraphic(v, "You clicked the screen at " + v.commaStr, 28, Red)))    
}