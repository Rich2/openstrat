/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pCanv._
import Colour._

case class Lesson2(canv: CanvasPlatform) extends pCanv.CanvasSimple
{
   val ptStart: Vec2 = 100 vv 100
   val ptEnd = 500 vv 300
   canv.bezierDraw(ptStart, 200 vv 250, 100 vv 270, ptEnd, 2, Gray)
   canv.textOutline(Vec2Z, "Text in outline", 40, Orange, 1)
   canv.textOutline(0 vv -200, "More text in outline", 60, Red, 2)
}