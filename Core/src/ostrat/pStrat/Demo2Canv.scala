/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pCanv._
import Colour._

case class Demo2Canv(canv: CanvasPlatform) extends pCanv.CanvasSimple
{
   def bd(c1: Vec2, c2: Vec2, colour: Colour) = BezierDraw(Vec2Z, c1, c2, 500 vv 350, 3, colour)  
  
   def stuff = List(
         
         ArcDraw(0 vv -200, -200 vv 0, 0 vv 0, 5, Colour.DarkRed).rotate(15.degs),
         ArcDraw(0 vv -400, -400 vv 0, 0 vv 0, 5, Colour.Blue),
         bd(-100 vv 200, 300 vv 400, Green),
         bd(-150 vv -50, 250 vv 350, Violet),
         bd(-250 vv 50, 200 vv 400, Orange),
         )
//   def stuff2(obj: Any) = stuff :+  TextGraphic.xy(0, 0, obj.toString, 20, Colour.Turquoise)
//   mouseUp = (v, b, s) =>   { repaint(stuff2(s.headOrElse("No clickable object on canvas"))); canv.bezierDraw(bd) }
   
   repaint(stuff)//2("Begin"))
   
}