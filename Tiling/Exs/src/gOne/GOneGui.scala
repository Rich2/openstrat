/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import pCanv._, geom._, prid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform, scenStart: OneScen) extends CmdBarGui("Game One Gui")
{
  statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  deb("Macro test")
  var scen = scenStart
  var history: Arr[OneScen] = Arr(scen)

  implicit def grid: HGrid = scen.grid

  def players: HCenArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: HCenArrOpt[HStep] = grid.newTileArrOpt[HStep]

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   *  moves. This data is state for the Gui. */
  var moves: HCenArrOpt[HStep] = NoMoves

  /** We could of used the mapHCen method and produced the units and the hexstrs graphics at the same time, but its easier to keep them separate. */
  def units: Arr[PolygonCompound] = players.mapHCenSomes { (hc, p) =>
    Rect(0.9, 0.6, hc.toPt2).fillDrawTextActive(p.colour, HPlayer(p, hc), p.toString + "\n" + hc.strComma, 24, 2.0)
  }

  /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
  def hexStrs: Arr[TextGraphic] = players.mapHCenNones(hc => TextGraphic(hc.strComma, 20, hc.toPt2))

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  val tiles: Arr[PolygonActive] = grid.activeTiles

  /** Draws the tiles sides (or edges). */
  val sidesDraw: LinesDraw = grid.sidesDraw()

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: Arr[LineSegDraw] = moves.mapHCenSomes { (hc, step) =>
    HCoordLineSeg(hc, hc.step(step)).lineSeg.draw(players.unSafeApply(hc).colour)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString, _ => {
    val getOrders = players.mapSomes2(moves)((player, step) => (player, step))
    scen = scen.endTurn(getOrders)
    moves = NoMoves
    repaint()
    thisTop()
  })

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn))

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headToStringElse("Nothing Selected")
      thisTop()
    }

    case (RightButton, List(HPlayer(p, hc1), HCen(y, c)), (hc2: HCen) :: _) =>
    { val newM: OptRef[HStep] = hc1.findStep(hc2)
      newM.foldDo{ if (hc1 == hc2) moves = moves.setNone(hc1) }(m => moves = moves.setSome(hc1, m))
      repaint()
    }
    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  def moveGraphics2: GraphicElems = moveGraphics.gridScale(scale).flatMap(_.arrow)

  def frame: GraphicElems = (tiles +- sidesDraw ++ units ++ hexStrs).gridScale(scale) ++ moveGraphics2
  def repaint(): Unit = mainRepaint(frame)
  repaint()
}