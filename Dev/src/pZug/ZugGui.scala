package ostrat
package pZug
import pCanv._, geom._, pGrid._

case class ZugGui(canv: CanvasPlatform) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = Zug1.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val sides = grid.sideLinesAll(scale).draw(2.0)
  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  mainRepaints(sides)
}
