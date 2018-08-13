/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pFormation
import pDisp._
import Colour._
import geom._

/** needs to inherit from MapCanvas */
case class BattleGui(canv: CanvasPlatform) extends pDisp.CanvasSimple//UnfixedMapGui
{
//   val rs = for { y <- 1 to 3
//      x <- 0 to 9
//      } yield Rect.xy(15, 10, (x - c.rankMiddle) * 20 + c.posn.x, (y - 2) * 20 + c.posn.y).fill(c.colour)  
//   repaint(rs)
}