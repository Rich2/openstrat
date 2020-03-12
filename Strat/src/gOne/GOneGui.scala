package ostrat
package gOne
import pCanv._, geom._, pGrid._

case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{
  val grid = OneGrid.grid
  val scale: Int = 40
  val ls = grid.tilesAllMap{ c =>
    val v = grid.coodToVec2Rel(c) * scale
    TextGraphic(grid.index(c).str + ": " + c.xyStr, 24, v)
  }
  repaint(ls)
}