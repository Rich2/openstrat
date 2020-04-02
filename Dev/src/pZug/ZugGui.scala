package ostrat
package pZug
import pCanv._, geom._, pGrid._

case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tilesOld = grid.mapPolygonsRel[GraphicElem, GraphicElems](scale){ (r, p) => p.fillTextActive(terrs(grid.index(r)).colour, r.toHexTile, r.ycStr, 16) }
  //val sidesOld = grid.sideLinesAllRel(scale).draw(2.0)
  val sides = grid.sideLinesAll.draw(2.0)
  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  mainRepaint(tilesOld +- sides.fTrans(v => (v - grid.cen) * scale))
}