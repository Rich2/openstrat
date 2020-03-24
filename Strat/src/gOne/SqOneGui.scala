package ostrat
package gOne
import pCanv._, pGrid._

case class SqOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Square Grid")
{
  val grid = new SquareGrid(2, 10, 2, 8)
  repaint(cenSideVertCoodText(grid, width, height))
}
