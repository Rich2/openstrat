package ostrat
package gDeux
import pCanv._

case class GDeuxGui(canv: CanvasPlatform, scenStart: DeuxScen) extends CmdBarGui("Game Dexu Gui")
{
  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(/*bTurn,*/ status))
  thisTop()
}
