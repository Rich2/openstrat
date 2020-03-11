package ostrat
package gOne
import pCanv._, geom._, pGrid._

case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{
  import HexGrid._
  val grid = OneGrid.grid

  val ls = grid.allTilesMap{c =>
    val v = coodToVec2(c) * 40
    TextGraphic(grid.index(c).str + ": " + c.xyStr, 24, v)
  }
  repaint(ls)

  //repaints(Rectangle(2).scale(200).fill(Colour.Green))
}