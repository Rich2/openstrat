/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import geom._, pCanv._, pGrid._

/** Main application for Unus Game. */
class UnusGui(canv: CanvasPlatform, grid: SimpGrid)
{
  val game = new Simplicissima(grid)
  deb(grid.str)
  val fg = 5
  debvar(fg)
  new UnusSetGui(canv, grid, game)
}

/** This needs tidying up. */
class UnusSetGui(val canv: CanvasPlatform, val grid: SimpGrid, val game: Simplicissima) extends TileGridGui[UTile, SideBare, SimpGrid]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin  
  var focus: Vec2 = grid.cen  

  override def mapObjs =
  {
    val tiles = tilesFlatMapAll { t =>
      val op = t.oPlayer.map { p =>
        val rect: GraphicElemsOld = Rectangle(120, 80, coodToDisp(t.cood)).fillActiveDrawText(p.colour, p, p.toString, 24, 2.0)
        val ol: Option[LineDraw] = p.move.map(newCood => CoodLine(t.cood, newCood).toLine2(coodToDisp).draw(2, p.colour))
        ol.toArr ++ rect
      }
      val a1: Arr[GraphicElem] = Arr(tileActiveOnly(t.cood, t), coodStrDisp(t.cood))
      a1.appendsOption(op)
    }
    tiles ++ sidesDrawAll()
  }

  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList//. fHead(Arr(), Arr(_))
      statusText = selected.headToStringElse("Nothing Selected")
      eTop()            
    }
    
    case (RightButton, Refs1(mp : MPlayer), Refs1(moveTile: UTile)) if grid.isTileCoodAdjTileCood(mp.cood, moveTile.cood) =>
      { statusText = mp.toString -- "move to" -- moveTile.cood.str
        val stCood = mp.cood
        val newMP = mp.copy(move = Some(moveTile.cood))
        grid.fSetTile(stCood, Some(newMP))
        rePanels
      }
    case (RightButton, Refs1(mp : MPlayer), Refs1(moveTile: UTile)) => setStatus(mp.toString -- "can not move to" -- moveTile.cood.str)

    case _ => setStatus("Other" -- clickList.toString)
  }   
  def turnCmd: MB0 = mb =>
    { val newGrid = game.newTurn(grid.getMoves)
      new UnusSetGui(canv, newGrid, game)
      ()
    }
  def saveCmd: MB0 = mb => setStatus("Save not implemented yet.")
  val bTurn = clickButton("T" -- grid.turnNum.str, turnCmd)
  val bSave = clickButton("S", saveCmd)
  override def eTop(): Unit = reTop(guButs ++ bTurn ++ bSave -+ status)
  mapPanel.backColour = Colour.Wheat
  rePanels
}