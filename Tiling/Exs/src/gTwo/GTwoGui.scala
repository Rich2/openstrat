/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import pCanv._, prid._, geom._, gOne._

case class GTwoGui(canv: CanvasPlatform, scenStart: TwoScen) extends SquareMapGui("Game Two Gui")
{
  statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def grid: SqGrid = scen.grid
  def players: SqCenArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  var yScale = grid.fullDisplayScale(mainWidth, mainHeight)

  def lunits = players.cMapSomes{ (p, sc) =>
    Rect(0.9, 0.6, sc.toPt2).fillDrawTextActive(p.colour, p, p.toString + "\n" + sc.rcStr, 24, 2.0)  }

  def css = players.cMapNones(hc => TextGraphic(hc.rcStr, 20, hc.toPt2))

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: SqCenArrOpt[SqAndStep] = NoMoves

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = clickButtonOld("Turn " + (scen.turn + 1).toString, _ => {
        val getOrders = moves.mapSomes(rs => rs)
        scen = scen.doTurn(getOrders)
        moves = NoMoves
        repaint()
        thisTop()
  })

  /** Draws the tiles sides (or edges). */
  val sidesDraw = grid.sidesDraw()

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: SqCenArrOpt[SqAndStep] = grid.newTileArrOpt[SqAndStep]

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, List(SPlayer(p, sc1), SqCen(y, c)), (sc2 : SqCen) :: _) =>
    {
      val newM: OptRef[SqStep] = sc1.optStep(sc2)
      //newM.foreach(m => moves = moves.setSomeNew(hc1, hc1.andStep(m)))
      repaint()
    }
    case (_, _, h) => deb("Other; " + h.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn, zoomIn, zoomOut))
  thisTop()
  def frame: GraphicElems = (lunits +- sidesDraw ++ css).gridScale(yScale)// ++ moveGraphics2
  repaint()
}
