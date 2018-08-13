/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pFormation
import pDisp._
<<<<<<< HEAD
import Colour._
import geom._

/** needs to inherit from MapCanvas */
case class BattleGui(canv: CanvasPlatform) extends pDisp.CanvasSimple//UnfixedMapGui
{
   val rs = for { y <- 1 to 3
      x <- 0 to 9}
   yield Rect.xy(30, 20, (x -4.5) * 40, (y - 2) * 40).fill(Blue)
=======
import geom._

/** needs to inherit from MapCanvas */
case class BattleGui(canv: CanvasPlatform, scen: BScen) extends pDisp.CanvasSimple//UnfixedMapGui
{
   val rs = scen.lunits.flatMap{c => 
   for { y <- 1 to c.ranks
      x <- 0 to c.rankLen
      }
   yield Rect.xy(15, 10, (x - c.rankMiddle) * 20 + c.posn.x, (y - 2) * 20 + c.posn.y).fill(c.colour)
   }
>>>>>>> 72180359d6a0d019c6b8e22992d05276ee86c59d
   repaint(rs)
}