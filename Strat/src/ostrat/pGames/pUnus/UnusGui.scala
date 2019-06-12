/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import geom._, pCanv._, pGrid._

/** Main application for Unus Game. */
class UnusGui(canv: CanvasPlatform, grid: UnusGrid)
{
  val game = new UnusGame(grid)
  new UnusSetGui(canv, grid, game)
}

/** This needs tidying up. */
class UnusSetGui(val canv: CanvasPlatform, val grid: UnusGrid, val game: UnusGame) extends TileGridGui[UTile, SideBare, UnusGrid]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin  
  var focus: Vec2 = grid.cen  
  override def mapObjs =
  { val tiles = tilesFlatMapAll{t => Arr(tileActiveOnly(t.cood, t), coodStrDisp(t.cood)) }
    val units =  grid.tilesOptionFlattenDispAll(_.oPlayer){(t, p) =>
      val rect = Rectangle(120, 80, coodToDisp(t.cood)).fillActiveDrawText(p.colour, p, p.toString, 24, 2.0)
      val arr = p.move.map(newCood => CoodLine(t.cood, newCood).toLine2(coodToDisp).draw(2, p.colour, -1))
      arr ++ rect
    }
    tiles ++ units ++ sidesDrawAll()
  }
  
  //optional members  
  
  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList.fHead(Arr(), Arr(_))
      statusText = selected.headOption.fold("Nothing Selected")(_.toString)
      eTop()            
    }
    
    case (RightButton, Arr(mp : MPlayer), Arr(moveTile: UTile)) if grid.isTileCoodAdjTileCood(mp.cood, moveTile.cood) =>
      {        
        statusText = mp.toString -- "move to" -- moveTile.cood.str
        val stCood = mp.cood
        val newMP = mp.copy(move = Some(moveTile.cood))
        grid.fSetTile(stCood, Some(newMP))
        rePanels
      }
    case (RightButton, Arr(mp : MPlayer), Arr(moveTile: UTile)) =>
      {        
        statusText = mp.toString -- "can not move to" -- moveTile.cood.str
        eTop()
      }
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
  def turnCmd: MB0 = mb =>
    {
      val newGrid = game.newTurn(grid.getMoves)
      new UnusSetGui(canv, newGrid, game)
    }
  val bTurn = clickButton("T" -- grid.turnNum.str, turnCmd)   
  override def eTop(): Unit = reTop(guButs :+ bTurn :+ status)   
  mapPanel.backColour = Colour.Wheat
  rePanels
}