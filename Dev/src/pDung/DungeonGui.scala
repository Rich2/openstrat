package ostrat
package pDung
import pCanv._, pGrid._, geom._
case class DungeonGui(canv: CanvasPlatform, scen: DungeonScen) extends CmdBarGui("Dungeon Gui")
{
  var statusText: String = "Welcome to Dungeon Gui"
  implicit def grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs.gridElem(r).colour, r.toHexTile, r.ycStr, 16) }
  val sls = grid.sidesDraw(2.0)

  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame: GraphicElems = (tiles +- sls).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
