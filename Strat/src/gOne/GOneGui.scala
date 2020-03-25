package ostrat
package gOne
import pCanv._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform) extends CmdBarGui("Game One Gui")
{ var statusText = "Stuff"
  val grid = OneGrid.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  reTop(Refs())
  mainRepaint(cenSideVertCoodText(grid, scale))
}