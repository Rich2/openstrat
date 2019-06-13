/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, pCanv._//, Colour._

case class LessonA9(canv: CanvasPlatform) extends CanvasSimple("Lesson A9")
{
  val rect = Rectangle(300, 200)
  val cen0 = -0 vv -400
  val x1 = 400
  val y2 = -100
  val cen1 = -x1 vv y2
  val arr1 = Arrow(cen0, cen1)
  val cen2 = x1 vv y2
  val arr2 = Arrow(cen0, cen2)
  def stuff = Arr(rect.slateDraw(cen0), rect.slateDraw(cen1), rect.slateDraw(cen2)) ++ arr1 ++ arr2
   repaint(stuff)   
}