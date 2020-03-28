/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import geom._, pCanv._, pGrid._

/** Main application for Unus Game. */
class UnusGui(canv: CanvasPlatform, grid: SimpGridOld)
{
  val game = new Simplicissima(grid)
  new UnusSetGui(canv, grid, game)
}

/** This needs tidying up. */
class UnusSetGui(val canv: CanvasPlatform, val grid: SimpGridOld, val game: Simplicissima) extends TileGridGui[UTileOld, SideOldBare, SimpGridOld]("Unus Game")
{
  //Required members
  var pScale: Double = scaleAlignMin  
  var focus: Vec2 = grid.cen  

  override def mapObjs =
  {
    val tiles = tilesFlatMapAll[GraphicElem, Refs[GraphicElem]] { t =>
      val op = t.oPlayer.map { p =>
        val rect = Rectangle(120, 80, coodToDisp(t.cood)).fillDrawTextActive(p.colour, p, p.toString, 24, 2.0)
        val ol: Option[LineDraw] = p.move.map(newCood => CoodLine(t.cood, newCood).toLine2(coodToDisp).draw(2, p.colour))
        ol.toArr +- rect
      }
      val a1: Refs[GraphicElem] = Refs(tileActiveOnly(t.cood, t), coodStrDisp(t.cood))
      a1.appendsOption(op)
    }
    tiles ++ sidesDrawAll()
  }

  mapPanel.mouseUp = (but: MouseButton, clickList, v) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList
      statusText = selected.headToStringElse("Nothing Selected")
      eTop()            
    }
    
    case (RightButton, (mp : MPlayer) :: _, List(moveTile: UTileOld)) if grid.isTileCoodAdjTileCood(mp.cood, moveTile.cood) =>
      { statusText = mp.toString -- "move to" -- moveTile.cood.str
        val stCood = mp.cood
        val newMP = mp.copy(move = Some(moveTile.cood))
        grid.fSetTile(stCood, Some(newMP))
        rePanels
      }
    case (RightButton, (mp : MPlayer) :: _, List(moveTile: UTileOld)) => setStatus(mp.toString -- "can not move to" -- moveTile.cood.str)

    case (RightButton, sel, _ ) => setStatus("Other" -- sel.toString() -- clickList.toString)
    case _ => //setStatus("Other" -- clickList.toString)
  }   
  def turnCmd: MouseCmd = mb =>
    { val newGrid = game.newTurn(grid.getMoves)
      new UnusSetGui(canv, newGrid, game)
      ()
    }
  def saveCmd: MouseCmd = mb => setStatus("Save not implemented yet.")
  val bTurn = clickButton("T" -- grid.turnNum.str, turnCmd)
  val bSave = clickButton("S", saveCmd)
  override def eTop(): Unit = reTop(guButs +- bTurn +- bSave +- status)
  mapPanel.backColour = Colour.Wheat
  rePanels
}