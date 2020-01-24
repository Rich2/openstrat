/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pCloseOrder
import pCanv._, geom._

case class BattleGui(canv: CanvasPlatform, scen: BScen) extends CanvasSimple("BattleGui")//UnfixedMapGui
{   
   val rs = scen.lunits.flatMap{c =>
      for { y <- 1 to c.ranks
      x <- 0 to c.rankLen
      } yield Rectangle(15, 10, (x - c.rankMiddle) * 20 + c.posn.x vv (y - 2) * 20 + c.posn.y).fill(c.colour)
   }
   repaintOld(rs)
} 