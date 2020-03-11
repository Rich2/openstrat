package ostrat
package gOne
import pCanv._, geom._, pGrid._

case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{
  import HexGrid._
  //val ls = OneGrid.grid.allTilesMap(c => coodToVec2(c)).scale(10).map(v => TextGraphic(v.str, 24, v))
  //repaint(ls)
  val grid = OneGrid.grid
  deb(grid.numOfTiles.toString)
  debvar(grid.row2sTileLen)
  debvar(grid.xRow2sMin)
  debvar(grid.yRow2sMin)
  debvar(grid.yRow0sMin)
  grid.allTilesForeach(c => deb(c.toString + "; " + grid.index(c).toString))
  repaints(Rectangle(2).scale(200).fill(Colour.Green))
}