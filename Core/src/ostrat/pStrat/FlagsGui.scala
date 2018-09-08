/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._, pCanv._, Colour._, FlagsZ._ 

case class FlagsGui(canv: CanvasPlatform) extends CanvasSimple
{   
   backColour = Gray
   
   val stuff = List(         
         france.scale(100).tR.slate(canv.topRight),
         chad.scale(100).tL.slate(canv.topLeft),                  
         belgium.scale(100).bL.slate(canv.bottomLeft),                  
         armenia.scale(100).bR.slate(canv.bottomRight),
         belgium.scale(100).slate(500, -100).rotate(Angle(math.Pi / 7)),
         us.scale(150).slate(400, 350),               
         uk.scale(200).slate(300, -350).rotate(10.degs),
         japan.scale(200).slate(650, 200).rotate(10.degs),
         
               )              
               
   mouseUp = (v, b, s) =>   { repaint(stuff)}//(s.headOrElse("No clickable object on canvas"))) }            
   repaint(stuff)      
 
}  
