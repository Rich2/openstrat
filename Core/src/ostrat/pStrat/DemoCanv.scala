/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pCanv._
import Colour._

case class DemoCanv(canv: CanvasPlatform) extends pCanv.CanvasSimple
{   
   val bd = new BezierDraw(0, 0, 100, 200, 300, 400, 350, 600, 14, Green)
  
   def stuff = List(         
         ArcDraw(Arc(0, -200, -200, 0, 0, 0), 5, Colour.DarkRed).rotate(15.degs),
         ArcDraw(Arc(0, -400, -400, 0, 0, 0), 5, Colour.Blue),
         //bd        
         )
//   def stuff2(obj: Any) = stuff :+  TextGraphic.xy(0, 0, obj.toString, 20, Colour.Turquoise)
//   mouseUp = (v, b, s) =>   { repaint(stuff2(s.headOrElse("No clickable object on canvas"))); canv.bezierDraw(bd) }
   
   repaint(stuff)//2("Begin"))
   canv.bezierDraw(bd) 
}