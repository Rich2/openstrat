package ostrat
package gOne
import pCanv._, pGrid._

case class SqOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Square Grid")
{
  val grid = new SquareGrid(2, 8, 2, 10)
  val scale = grid.fullDisplayScale(width, height)
  repaint(cenSideVertCoodText(grid, scale))
}
