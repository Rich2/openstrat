package ostrat
package gOne
import pCanv._, geom._

/** Graphical user interface for GOne example game. */
case class IrrGui(canv: CanvasPlatform) extends CanvasNoPanels("Irregular Hex Grid Gui")
{
  val grid = Irr1.grid
  /*val scale: Double = grid.fullDisplayScale(width, height)
  val cenTexts = grid.tilesCoodVecMap(scale){ (c, v) =>  TextGraphic(c.yxStr, 26, v) }
  val sls: LinesDraw = grid.sideLinesAll(scale).draw(2.0)
  val sideTexts = grid.sidesCoodVecMap(scale){ (c, v) =>  TextGraphic(c.yxStr, 22, v, Colour.Blue) }
  val vertTexts = grid.vertsCoodVecMap(scale){ (c, v) =>  TextGraphic(c.yxStr, 20, v, Colour.Red) }
*/
  repaint(cenSideVertCoodText(grid, width, height))//    cenTexts +- sls ++ sideTexts ++ vertTexts)
}