package ostrat
package pDung
import pCanv._
case class DungeonGui(canv: CanvasPlatform) extends CmdBarGui("Dungeon Gui")
{
  var statusText: String = "Welcome to Dungeon Gui"

  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
}
