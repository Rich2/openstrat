package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform) extends CanvasNoPanels("Game One Gui")
{
  val grid = OneGrid.grid
  val scale: Int = 40
  grid.sideCoodsOfTile(2 cc 2).foreach(println)
  val ls = grid.tilesAllMap{ c =>
    val v = grid.coodToVec2Rel(c) * scale
    TextGraphic(grid.index(c).str + ": " + c.xyStr, 24, v)
  }
  val sls = grid.sideLinesAllRel.map(l2 => l2.fTrans(v => v  * scale)).draw(2.0)
 // val sls = grid.sideCoodsOfTile(2 cc 2).map(l2 => grid.(l2).toL l2.fTrans(v => v  * scale)).draw(2.0)

  repaint(ls +- sls)
}