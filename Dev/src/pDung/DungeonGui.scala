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
  val players = scen.characs.gridMapSomes { (r, cp) =>
    val poly1: Polygon = Rectangle(1.5).insVerts(1, -0.25 vv 0.5, 0 vv 0.8, 0.25 vv 0.5).rotate(cp.facing.angle)
    val poly2: Polygon = poly1.scale( 0.65).slate(r.gridVec2)//.slate(tog.cen)
    poly2.fillDraw(cp.Charac.colour)//DrawText(charac, charac.colour, 1), TextGraphic(charac.iden.toString, 16, cen, charac.colour.contrast))
  }

  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame: GraphicElems = (tiles +- sls ++ players).gridTrans(scale)
  def repaint() = mainRepaint(frame)
  repaint()
}
