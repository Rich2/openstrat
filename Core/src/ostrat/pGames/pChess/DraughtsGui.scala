/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import geom._
import pGrid._
import pDisp._
import Colour._

class DraughtsGui(canv: CanvasPlatform) extends SquareGridGui[CheckersSq, CheckersBoard](canv, CheckersBoard())
{
   var player = true
   def fSquare: OfSquareReg[CheckersSq, CheckersBoard] => Disp2 = tog =>
      {
         import tog._
         val colour: Colour = tile.colour
         val poly = vertVecs
         val tv = poly.fill(colour)
         val ch = tile match {
            case DarkSq(_, _, Some(b)) =>
               {
                  def colour = b.fold(Black, White)
                  Some(Circle.fillSubj(cen, psc, colour.toString, colour))
               }
            case _ => None
         }         
         Disp2(List(tv), ch.toList)
      }
   def mapObjs: CanvObjs = ofTilesDisplayFold(fSquare).collapse
   
   eTop()
   mapPanel.repaint(mapObjs)
   mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
   {
      case (LeftButton, _, _) =>
         {
            selected = clickList.fHead(Nil, (h , _) => List(h))
            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
            eTop()
         }
         case _ => //deb("Mouse other")
   }    
}



