/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.learn
import ostrat._, geom._, pCanv._

case class LessonB1(canv: CanvasPlatform) extends CanvasSimple("Lesson B1")
{
  timedRepaint1(e => TextGraphic(Vec2Z, (e /1000).toString + " Seconds have elapsed", 45))    
}