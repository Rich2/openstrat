package ostrat
package gDeux
import pCanv._

case class DeuxGui(canv: CanvasPlatform, scenStart: DeuxScen) extends CmdBarGui("Game Dexu Gui")
{
  var statusText = "Let click on Player to select. Right click on adjacent Hex to set move."
}
