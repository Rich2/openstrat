package ostrat
package gOne
import pCanv._, geom._

/** Graphical user interface for GOne example game. */
case class IrrGui(canv: CanvasPlatform) extends CanvasNoPanels("Irregular Hex Grid Gui")
{
  val grid = Irr1.grid
  val scale: Double = grid.fullDisplayScale(width, height)

  val ls2 = grid.tilesCoodVecMap(scale){ (c, v) =>  TextGraphic(grid.index(c).str + ": " + c.yxStr, 24, v) }

  val sls: LinesDraw = grid.sideLinesAllRel(scale).draw(2.0)

  repaint(ls2 +- sls)
}