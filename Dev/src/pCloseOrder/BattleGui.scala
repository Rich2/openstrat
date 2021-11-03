/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCloseOrder
import pgui._, geom._

/** Beginnings of a game to explore close formation battles. */
case class BattleGui(canv: CanvasPlatform, scen: BScen) extends CanvasNoPanels("BattleGui")
{
  def lunits = scen.lunits

  def rs: GraphicElems = lunits.flatMap { c =>
    ijToMap(1, c.ranks)(0, c.rankLen) { (y, x) =>
      Rect(15, 10, (x - c.rankMiddle) * 20 + c.posn.x pp (y - 2) * 20 + c.posn.y).fill(c.colour)
    }
  }
   repaint(rs)
} 