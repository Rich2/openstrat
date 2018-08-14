/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pFormation
import pDisp._
import geom._

case class BattleGui(canv: CanvasPlatform, scen: BScen) extends pDisp.CanvasSimple//UnfixedMapGui
{   
   val rs = scen.lunits.flatMap{c =>
      for { y <- 1 to c.ranks
      x <- 0 to c.rankLen
      } yield Rectangle(15, 10, (x - c.rankMiddle) * 20 + c.posn.x, (y - 2) * 20 + c.posn.y).fill(c.colour)
   }
   repaint(rs)
} 