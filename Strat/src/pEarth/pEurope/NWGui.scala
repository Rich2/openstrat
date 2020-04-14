package ostrat
package pEarth
package pEurope
import pCanv._, pGrid._, geom._

case class NWGui(canv: CanvasPlatform, scen: E80Data, cenRoord: Roord) extends CmdBarGui("North West Europe Gui")
{
  implicit val grid = scen.grid
  val scale = 40
  val terrs = EuropeNWTerr.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides = grid.sideLines.draw(2.0)

  var statusText = "Tile Grid for North West Europe"
  def thisTop(): Unit = reTop(Arr(status))
  thisTop()
  def frame = (tiles +- sides).gridRoordTrans(cenRoord, scale)
  mainRepaint(frame)
}