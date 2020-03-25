package ostrat
package gOne
import pCanv._, geom._

/** Graphical user interface for GOne example game. */
case class IrrGui(canv: CanvasPlatform) extends CanvasNoPanels("Irregular Hex Grid Gui")
{ val grid = Irr1.grid
  val scale = grid.fullDisplayScale(width, height)
  repaint(cenSideVertCoodText(grid, scale))
}