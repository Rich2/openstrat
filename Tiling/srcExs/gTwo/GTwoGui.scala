package ostrat
package gTwo
import pCanv._, prid._, geom._

case class GTwoGui(canv: CanvasPlatform, scenStart: TwoScen) extends CmdBarGui("Game Dexu Gui")
{
  var statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def grid = scen.grid
  def players: SqCenArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

  def lunits = players.cMapSomes{ (p, sc) =>
    Rect(0.9, 0.6, sc.toPt2).fillDrawTextActive(p.colour, p, p.toString + "\n" + sc.rcStr, 24, 2.0)  }

  def css = players.cMapNones(hc => TextGraphic(hc.rcStr, 20, hc.toPt2))

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = clickButtonOld("Turn " + (scen.turn + 1).toString, _ => {
    //    val getOrders = moves.mapSomeOnlys(rs => rs)
    //    scen = scen.turn(getOrders)
    //    moves = NoMoves
    //    repaint()
    //    thisTop()
  })

  /** Draws the tiles sides (or edges). */
  val sidesDraw = grid.sidesDraw()

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  //def NoMoves: HcenArrOpt[HCAndStep] = grid.newTileArrOpt[HCAndStep]

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()
  def frame: GraphicElems = (lunits +- sidesDraw ++ css).gridScale(scale)// ++ moveGraphics2
  //(tiles +- sidesDraw ++ roardTexts ++ lunits ).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
