/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pCloseOrder
import pCanv._, geom._

case class BattleGui(canv: CanvasPlatform, scen: BScen) extends CanvasNoPanels("BattleGui")//UnfixedMapGui
{   
   val rs: GraphicElems = scen.lunits.flatMapOld { c =>
      ijToMap(1, c.ranks)(0, c.rankLen) { (y, x) =>
         Rectangle(15, 10, (x - c.rankMiddle) * 20 + c.posn.x vv (y - 2) * 20 + c.posn.y).fill(c.colour)
      }
   }
   repaint(rs)
} 