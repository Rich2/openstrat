package ostrat
package gDeux
import pCanv._, prid._, geom._

case class GDeuxGui(canv: CanvasPlatform, scenStart: DeuxScen) extends CmdBarGui("Game Dexu Gui")
{
  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart
  implicit def grid = scen.grid
  def players: SqcenArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)

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


  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn))
  thisTop()
  def frame: GraphicElems = Arr(sidesDraw).gridScale(scale)// ++ moveGraphics2
  //(tiles +- sidesDraw ++ roardTexts ++ lunits ).gridScale(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
