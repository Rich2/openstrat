/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pChess
import geom._
import pGrid._
import pDisp._
import Colour._

class DraughtsGui(canv: CanvasLike) extends SquareGridGui[CheckersSq, CheckersBoard](canv, CheckersBoard())
{
   var player = true
   def fSquare: OfSquareReg[CheckersSq, CheckersBoard] => Disp2 = tog =>
      {
         import tog._
         val colour: Colour = tile.colour
         val poly = vertVecs
         val tv = poly.fill(colour)
         val ch = tile match {
            case DarkSq(_, _, Some(b)) => Some(Circle.fill(cen, psc, b.fold(Black, White)))
            case _ => None
         }         
         Disp2(List(tv), ch.toList)
      }
   def mapObjs: CanvObjs = ofTilesDisplayFold(fSquare).collapse
   
   eTop()
   mapPanel.repaint(mapObjs)
}



