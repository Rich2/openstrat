/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pChess
import geom._
import pGrid._
import pDisp._
import Colour._

class Draughts(canv: CanvasLike) extends SquareGridGui[CheckersSq, CheckersBoard](canv, CheckersBoard())
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

sealed trait CheckersSq extends Tile
{
   def colour: Colour
}
object CheckersSq
{
   implicit object CheckerSsqIsType extends IsType[CheckersSq]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[CheckersSq]
      override def asType(obj: AnyRef): CheckersSq = obj.asInstanceOf[CheckersSq]   
   }   
}

case class LightSq(x: Int, y: Int) extends CheckersSq { def colour = Cornsilk }
case class DarkSq(x: Int, y: Int, piece: Option[Boolean]) extends CheckersSq { def colour = Green }

object DarkSq
{
   def apply(piece: Option[Boolean]): (Int, Int) => DarkSq= DarkSq(_, _, piece)
}

/** Not happy with this obviously. But its a start. */
class CheckersBoard extends ChessBoardLike[CheckersSq]
{
   tilesSetAll(LightSq.apply)   
   for (x <- 2 to 8 by 2) fSetSquare(x, 8, DarkSq(Some(false)))
   for (x <- 1 to 7 by 2) fSetSquare(x, 7, DarkSq(Some(false)))
   for (x <- 2 to 8 by 2) fSetSquare(x, 6, DarkSq(Some(false)))
   for (x <- 1 to 7 by 2) fSetSquare(x, 5, DarkSq(None))
   for (x <- 2 to 8 by 2) fSetSquare(x, 4, DarkSq(None))
   for (x <- 1 to 7 by 2) fSetSquare(x, 3, DarkSq(Some(true)))
   for (x <- 2 to 8 by 2) fSetSquare(x, 2, DarkSq(Some(true)))
   for (x <- 1 to 7 by 2) fSetSquare(x, 1, DarkSq(Some(true)))
}

object CheckersBoard
{
   def apply(): CheckersBoard = new CheckersBoard
}

