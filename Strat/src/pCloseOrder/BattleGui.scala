/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCloseOrder
import pCanv._, geom._

case class BattleGui(canv: CanvasPlatform, scen: BScen) extends CanvasNoPanels("BattleGui")
{
  def lunits = scen.lunits
  debvar(lunits.length)
   def rs: GraphicElems = lunits.flatMap { c =>
      ijToMap(1, c.ranks)(0, c.rankLen) { (y, x) =>
         Rectangle(15, 10, (x - c.rankMiddle) * 20 + c.posn.x vv (y - 2) * 20 + c.posn.y).fill(c.colour)
      }
   }
   debvar(rs.length)
   //debvar(rs(1).toString)
   //repaint(rs +- Rectangle(1.5).scale(100).fill(Colour.Green))
} 