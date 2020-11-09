/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package gUne
import pCanv._, geom._, prid._

/** Graphical user interface for GOne example game. */
case class GUneGui(canv: CanvasPlatform, scenStart: UneScen) extends CmdBarGui("Game Une Gui")
{
  var statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart

  implicit def grid = scen.grid
  def players: HexArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: HexArrOpt[HCAndStep] = grid.newTileArrOpt[HCAndStep]

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: HexArrOpt[HCAndStep] = NoMoves

  def lunits = players.mapSomeWithHCens{ (hc, p) => Rect(0.9, 0.6, hc.toPt2).fillDrawTextActive(p.colour, HPlayer(p, hc),
    p.toString + "\n" + hc.rcStr, 24, 2.0) }

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  val tiles = grid.activeTiles

  /** Gives the tiles Roord. Its Row based integer coordinate. */
 // val roardTexts = grid.cenRoordIndexTexts() ++ grid.sideRoordIndexTexts() ++ grid.vertRoordIndexTexts()

  /** Draws the tiles sides (or edges). */
  val sidesDraw = grid.sidesDraw(2.0)

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: Arr[LineSegDraw] = moves.mapSomes{ rs => HCoordLineSeg(rs.hc1, rs.hc2).toLine2.draw(players(rs.hc1).colour) }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = clickButtonOld("Turn " + (scen.turn + 1).toString, _ => {
//    val getOrders = moves.mapSomeOnlys(rs => rs)
//    scen = scen.turn(getOrders)
//    moves = NoMoves
//    repaint()
//    thisTop()
  })

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn, status))

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
    { case (LeftButton, _, cl) =>
      { selected = cl
        statusText = selected.headToStringElse("Nothing Selected")
        thisTop()
      }

      case (RightButton, List(HPlayer(p, hc1), HCen(y, c)), (hc2 : HCen) :: _) =>
      {
        val newM: OptRef[HCStep] = hc1.optStep(hc2)
        newM.foreach(m => moves = moves.setSome(hc1, hc1.andStep(m)))
        repaint()
      }
       case (_, _, h) => deb("Other; " + h.toString)
    }
  thisTop()
  def moveGraphics2 = moveGraphics.gridScale(scale).map(_.arrow)//  .toLine2.drawArrow(players(rs.hc1).colour)
  def frame: GraphicElems = (tiles +- sidesDraw ++ lunits).gridScale(scale) ++ moveGraphics2
  //(tiles +- sidesDraw ++ roardTexts ++ lunits ).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}