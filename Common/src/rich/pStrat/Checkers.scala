/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pStrat
import geom._
import pGrid._
import pDisp._
import Colour._

class Checkers(canv: CanvDisp) extends SquareGridGui[CheckersSq, CheckersBoard.type](canv, CheckersBoard)
{
   def fSquare: RegSquareOfGrid[CheckersSq, CheckersBoard.type] => Disp2 = tog =>
      {
         val tile = tog.tile
         
         val colour: Colour = tile.colour
         val poly = tog.vertVecs
         val tv = poly.fill(colour)
         
         Disp2(List(tv), Nil)
      }
   def mapObjs: CanvObjs =  ofTilesDisplayFold(fSquare).collapse
   
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

/** Not happy with this obviously. But its a start. */
object CheckersBoard extends SquareGrid[CheckersSq](2, 16, 2, 16)
{
   tilesSetAll(LightSq.apply)   
   for (x <- 4 to 16 by 4) setTile(x, 16, DarkSq(x, 16, Some(false)))
   for (x <- 2 to 14 by 4) setTile(x, 14, DarkSq(x, 14, Some(false)))
   for (x <- 4 to 16 by 4) setTile(x, 12, DarkSq(x, 12, Some(false)))
   for (x <- 2 to 14 by 4) setTile(x, 10, DarkSq(x, 10, None))
   for (x <- 4 to 16 by 4) setTile(x, 8, DarkSq(x, 8, None))
   for (x <- 2 to 14 by 4) setTile(x, 6, DarkSq(x, 6, Some(true)))
   for (x <- 4 to 16 by 4) setTile(x, 4, DarkSq(x, 4, Some(true)))
   for (x <- 2 to 14 by 4) setTile(x, 2, DarkSq(x, 2, Some(true)))
}
