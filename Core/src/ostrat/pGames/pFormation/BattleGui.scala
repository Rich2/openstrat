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
   val rs = for { y <- 1 to 3
      x <- 0 to 9}
   yield Rect.xy(30, 20, (x -4.5) * 40, (y - 2) * 40).fill(Blue)
   repaint(rs)
}