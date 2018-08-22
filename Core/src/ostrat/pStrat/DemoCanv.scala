/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pCanv._
import Colour._
case class DemoCanv(canv: CanvasPlatform) extends pCanv.CanvasSimple
{
   val r1 = Circle.fillSubj(500, "This is a red Circle", Red)
   val r2 = Circle.fillSubj(500, "This is pink circle", Pink, -500, 0)
   //val r1 = Rectangle(300, 200).draw(5.0, Blue)
   def stuff = List(r1, r2,
         Rectangle.curvedSegs(340, 260, 50).slate(200, 0).fillDraw(Brown, 1).rotate(Angle(math.Pi / 7)),
         Square.curvedSegs(140, 40).slate(-250, 300).draw(1.0, Blue).rotate(Angle(math.Pi / 16)),
         Rectangle.curvedSegs(340, 260, 40).slate(-500, 0).rotate(15.degs).fillDraw(Colour.BlanchedAlmond, 5),
         TextGraphic.xy(-500, 0, "Passive curved Rectangle", 12).rotate(15.degs),
         
         br.draw(2, Colour.Green),
         ArcDraw(Arc(0, -200, -200, 0, 0, 0), 5, Colour.DarkRed).rotate(15.degs),
         ArcDraw(Arc(0, -200, -200, 0, 0, 0), 5, Colour.Blue),
         r1
         )
         
         
   val br: List[ShapeSeg] = Rectangle.curvedSegs(480, 260, 40).slate(0, -150).rotateRadians(math.Pi / 8)            
   
   def stuff2(obj: Any) = stuff :+  TextGraphic.xy(0, 0, obj.toString, 20, Colour.Turquoise)
   mouseUp = (v, b, s) =>   { repaint(stuff2(s.headOrElse("No clickable object on canvas"))) }
   repaint(stuff2("Begin"))
}