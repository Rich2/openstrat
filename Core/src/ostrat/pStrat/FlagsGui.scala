/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pDisp._

case class FlagsGui(canv: CanvasPlatform) extends pDisp.CanvasSimple
{      
   import Colour._
   import Flags._ 
   backColour = Gray
   val r1 = Rectangle(300, 200).draw(5.0, Blue)   
   val br: List[ShapeSeg] = Rectangle.curvedSegs(480, 260, 40).slate(0, -150).rotateRadians(math.Pi / 8)        
   val stuff = List(         
         france.scale(100).tR.slate(canv.topRight),
         chad.scale(100).tL.slate(canv.topLeft),                  
         belgium.scale(100).bL.slate(canv.bottomLeft),                  
         armenia.scale(100).bR.slate(canv.bottomRight),
         belgium.scale(100).slate(500, -100).rotate(Angle(math.Pi / 7)),
         us.scale(150).slate(400, 350),               
         uk.scale(200).slate(300, -350).rotate(10.degs),
         japan.scale(200).slate(650, 200).rotate(10.degs),
         Rectangle.curvedSegs(340, 260, 50).slate(200, 0).fillDraw(Brown, 1).rotate(Angle(math.Pi / 7)),
         Square.curvedSegs(140, 40).slate(-250, 300).draw(1.0, Blue).rotate(Angle(math.Pi / 16)),
         Rectangle.curvedSegs(340, 260, 40).slate(-500, 0).rotate(15.degs).fillDraw(Colour.BlanchedAlmond, 5),
         FillText.xy(-500, 0, "Passive curved Rectangle", 12).rotate(15.degs),
         
         br.draw(2, Colour.Green),
         DrawArc(Arc(0, -200, -200, 0, 0, 0), 5, Colour.DarkRed).rotate(15.degs),
         DrawArc(Arc(0, -200, -200, 0, 0, 0), 5, Colour.Blue),
         r1
               )
   def stuff2(obj: Any) = stuff :+  FillText.xy(0, 0, obj.toString, 20, Colour.Turquoise)            
               
   mouseUp = (v, b, s) =>   { repaint(stuff2(s.headOrElse("No clickable object on canvas"))) }            
   repaint(stuff2("Click on canvas"))
      
 
}  
