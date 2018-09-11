/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pLearn
import geom._, pCanv._, Colour._

case class Lesson2(canv: CanvasPlatform) extends CanvasSimple
{
  val ptStart: Vec2 = 100 vv 100
  val ptEnd = 500 vv 300
  canv.bezierDraw(ptStart, 200 vv 250, 100 vv 270, ptEnd, 2, Gray)
  canv.textOutline(Vec2Z, "Text in outline", 40, Orange, 1)
  canv.textOutline(0 vv -200, "More text in outline", 60, Red, 2)
  
   // The following commands take variable numbers of parameters. You can add / remove parameters to see the effect
  canv.polyFill(Orange, -300 vv 200, -300 vv 300, -250 vv 300)
  canv.polyDraw(2, Blue, -250 vv 300, -200 vv 325, -150 vv 300, -275 vv 200)   
  
}