package ostrat
package gOne
import ostrat.geom.LinesDraw
import pCanv._
import pGrid._

case class SqOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Square Grid")
{
  val grid = new SquareGrid(2, 8, 2, 10)
  val scale = grid.fullDisplayScale(width, height)
  val sls: LinesDraw = grid.sideLinesAllRel(scale).draw(2.0)
  repaint(sls +: cenSideVertRoordTextRel(grid, scale))
}
