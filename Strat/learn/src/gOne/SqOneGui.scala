package ostrat
package gOne
import geom._, pCanv._, pGrid._

case class SqOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Square Grid")
{
  implicit val grid = new SquareGridSimple(2, 8, 2, 10)
  val scale = grid.fullDisplayScale(width, height)
  val sls: LinesDraw = grid.sidesDraw(2.0)
  val csvr = grid.cenSideVertRoordText
  val frame = (sls +: csvr).gridScale(scale)
  repaint(frame)
}
