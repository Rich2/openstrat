package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class IrrGui(canv: CanvasPlatform) extends CanvasNoPanels("Irregular Hex Grid Gui")
{ implicit val grid = Irr1.grid
  val scale = grid.fullDisplayScale(width, height)
  val sld: LinesDraw = grid.sidesAllDraw(2.0)
  val csvr = grid.cenSideVertRoordText
  val frame = (sld +: csvr).gridTrans(scale)
  repaint(frame)
}