package ostrat
package pEarth
import pCanv._, pGrid._

case class E80GridGui(canv: CanvasPlatform, scen: E80Data, cenRoord: Roord) extends CmdBarGui("North West Europe Gui")
{
  implicit val grid = scen.grid
  val scale = 40
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides = grid.sideLines.draw(2.0)

  var statusText = "Tile Grid for North West Europe"
  def thisTop(): Unit = reTop(Arr(status))
  thisTop()
  def frame = (tiles +- sides).gridRoordTrans(cenRoord, scale)
  mainRepaint(frame)
}