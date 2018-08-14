/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import geom._
import pGrid._
import pDisp._
import Colour._

case class DraughtsGui(canv: CanvasPlatform) extends CanvasSimple// SquareGridGui[CheckersSq, SideBare, CheckersBoard](canv, CheckersBoard())
{
   var player = true
   def fSquare: OfSquareReg[CheckersSq, SideBare, CheckersBoard] => Disp2 = tog =>
      {
         import tog._
         val colour: Colour = tile.colour        
         val tv = vertDispVecs.fill(colour)
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
    val darkSquareColour = Brown
    val lightSquareColour = Pink
    
    val rowSize = 8
    val rowCen = (1.0 + rowSize) / 2.0
    val margin = 15
    val tileWidth = ((height.min(width) - margin * 2).max(100) / rowSize)
    val coods = for { y <- 1 to rowSize; x <- 1 to rowSize } yield Cood(x, y)
    implicit class AdjInt(i: Int){ def adj = i - rowCen}
    val stuff = coods.map
    {         
       case Cood(x, y) if x.isOdd & y.isOdd | x.isEven & y.isEven =>
          Square.fill(tileWidth, darkSquareColour, tileWidth * x.adj, tileWidth * y.adj)      
       case Cood(x, y) => Square.fill(tileWidth, lightSquareColour, tileWidth * x.adj, tileWidth * y.adj)          
    }
    repaint(stuff)
   //def mapObjs: CanvObjs = ofSTilesDisplayFold(fSquare).collapse   
   //mapPanel.repaint(mapObjs)
   //mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
//   {
//      case (LeftButton, _, _) =>
//         {
//            selected = clickList.fHead(Nil, (h , _) => List(h))
//            statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
//            eTop()
//         }
//         case _ => //deb("Mouse other")
//   }    
}



