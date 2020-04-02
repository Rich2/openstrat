package ostrat
package pZug
import pCanv._, pGrid._

case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs.gridIndex(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides = grid.sideLinesAll.draw(2.0)
  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame = (tiles +- sides).gridTrans(scale)
  mainRepaint(frame)
}