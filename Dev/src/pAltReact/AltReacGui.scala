/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pAltReact
import pgui._, prid._, geom._

case class AltReacGui(canv: CanvasPlatform, rows: Int, columns: Int) extends CmdBarGui("Alternative Reactor")
{
  statusText = "Welcome to alternative ReactorGui."

  var scen = AltScen.start(rows, columns)
  implicit def grid: SqGrid = scen.grid
  def balls = scen.balls

  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  /** Draws the tiles sides (or edges). */
  val sidesDraw = grid.sidesDraw()

  def ballDisps: GraphicElems = ??? //balls.map
    /*players.mapHcenSomes{ (hc, p) => Rect(0.9, 0.6, hc.toPt2).fillDrawTextActive(p.colour, HPlayer(p, hc),
    p.toString + "\n" + hc.rcStr, 24, 2.0) }*/

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = clickButtonOld("Turn " + (scen.turn + 1).toString, _ => {
    //    val getOrders = moves.mapSomeOnlys(rs => rs)
    //    scen = scen.turn(getOrders)
    //    moves = NoMoves
    //    repaint()
    //    thisTop()
  })

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()
  def frame: GraphicElems = Arr(sidesDraw).gridScale(scale)// ++ moveGraphics2
  //(tiles +- sidesDraw ++ roardTexts ++ lunits ).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}