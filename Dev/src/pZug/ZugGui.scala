package ostrat
package pZug
import pCanv._, pGrid._, pStrat._

case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs.gridIndex(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides = grid.sideLinesAll.draw(2.0)

  val lunits = scen.lunits.gridHeadsMap{(roord, squad) => UnitCounters.infantry(0.5, "Hi", Colour.Green, Colour.Orange).slate(grid.roordToVec2(roord)) }

  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame = (tiles +- sides ++ lunits).gridTrans(scale)
  mainRepaint(frame)
}