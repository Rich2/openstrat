package ostrat
package pZug
import pCanv._, pGrid._, pStrat._

case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs.gridElem(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides = scen.sTerrs.gridMap{(r, b) =>
    val sl = grid.sideRoordToLine2(r)
    ife(b, sl.draw(8, Colour.Gray), sl.draw(2))
  }
  //val sides = grid.sideLines.draw(2.0)

  val lunits = scen.lunits.gridHeadsMap{ (roord, squad) =>
    UnitCounters.infantry(0.6, squad, squad.colour, terrs.gridElem(roord).colour).slate(roord.gridVec2)
  }

  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  def frame = (tiles ++ sides ++ lunits).gridTrans(scale)
  mainRepaint(frame)
}