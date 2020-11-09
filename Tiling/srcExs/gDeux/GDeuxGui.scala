package ostrat
package gDeux
import pCanv._

case class GDeuxGui(canv: CanvasPlatform, scenStart: DeuxScen) extends CmdBarGui("Game Dexu Gui")
{
  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart

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
  thisTop()
}
