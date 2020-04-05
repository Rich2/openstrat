package ostrat
package pEarth
package pEurope
import pCanv._, pGrid._, pStrat._, e80Grid._

case class NWGui(canv: CanvasPlatform) extends CmdBarGui("North West Europe Gui")
{
  implicit val grid = EuropeNWTerr.grid
  val scale = 40
  val terrs = EuropeNWTerr.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs.gridElem(r).colour, r.toHexTile, r.ycStr, 16) }


  var statusText = "Hello "
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame = (tiles).gridTrans(scale)
  mainRepaint(frame)
}
