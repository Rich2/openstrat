/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

package pStrat
import geom._
import pCanv._
import Colour._

case class Demo1Canv(canv: CanvasPlatform) extends pCanv.CanvasSimple
{   
   canv.lineDraw(Vec2Z, 100 vv 100)// 0 vv 0 is the same as Vec2Z
   canv.lineDraw(0 vv 50, 150 vv 200, 3)
   canv.lineDraw(50 vv 0, 200 vv 0, 2, Red)
   
   canv.arcDraw(-200 vv 0, 0 vv 200, Vec2Z)
   canv.arcDraw(-220 vv 0, 0 vv 220, Vec2Z, 4, Pink)
   canv.bezierDraw(200 vv -350, -500 vv -300, -600 vv -300, -450 vv -200, 2, Green)
}