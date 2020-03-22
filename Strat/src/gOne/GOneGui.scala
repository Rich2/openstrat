package ostrat
package gOne
import pCanv._, geom._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{
  val grid = OneGrid.grid
  val scale: Int = 120

  val ls2 = grid.tilesAllCoodVecMap(scale){ (c, v) =>  TextGraphic(grid.index(c).str + ": " + c.yxStr, 24, v) }

  val sls: LinesDraw = grid.sideLinesAllRel().map(_.scale(scale)).draw(2.0)

  repaint(ls2  +- sls)
}