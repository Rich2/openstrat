package ostrat
package gOne
import pCanv._, geom._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{ val grid = OneGrid.grid
  repaint(cenSideVertCoodText(grid, width, height))
}