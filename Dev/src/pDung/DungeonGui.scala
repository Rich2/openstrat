package ostrat
package pDung
import pCanv._, pGrid._
case class DungeonGui(canv: CanvasPlatform, scen: DungeonScen) extends CmdBarGui("Dungeon Gui")
{
  var statusText: String = "Welcome to Dungeon Gui"
  implicit def grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val sls = grid.sidesDraw(2.0)


  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame = (Refs(sls)).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
