/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import geom._, pCanv._, pGrid._

/** Main application for Unus Game. */
class UnusGui(canv: CanvasPlatform, grid: UnusGrid)
{
  new UnusSetGui(canv, grid)
}

class UnusSetGui(val canv: CanvasPlatform, val grid: UnusGrid) extends TileGridGui[UTile, SideBare, UnusGrid]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin
  deb(pScale.toString)
  var focus: Vec2 = grid.cen  
  override def mapObjs =
    tilesFlatMapListAll{t => List(tileActiveOnly(t.cood, t), coodStrDisp(t.cood)) } :::
    grid.tilesOptionFlattenDispAll(t => t.oPlayer.map(p => Rectangle(120, 80, coodToDisp(t.cood)).fillDrawActive(p.colour, p, 2)))  :::
    sidesDrawAll()
  
  //optional members
  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList.fHead(Nil, List(_))
      statusText = selected.headOption.fold("Nothing Clicked")(_.toString)
      eTop()            
    }
    
    case (RightButton, List(p : Player), List(newTile: UTile)) => deb("Move")
    case (RightButton, List(p : Player), l) => deb("PlayerMove" -- l.toString)
//      scen.zPath(squad.cood, newTile.cood).foreach{l =>
//      squad.action = Move(Coods.fromSeq(l))
//      repaintMap
//    }
//    
//    case (MiddleButton, List(squad : Squad), List(newTile: ZugTile)) => { squad.action = Fire(newTile.cood); repaintMap }
//    
//    case (RightButton, List(squad : Squad), List(newTile: ZugTile)) => deb("No Move" -- squad.cood.toString -- newTile.cood.toString)
    
    case _ => deb("Other" -- clickList.toString)
  }   
    
  mapPanel.backColour = Colour.Wheat
  eTop()
  repaintMap
}