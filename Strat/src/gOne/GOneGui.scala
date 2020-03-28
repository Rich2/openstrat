package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform) extends CmdBarGui("Game One Gui")
{ var statusText = "Stuff"
  val grid = OneGrid.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val units = grid.mapArrTileOptRefwithVec(OneGrid.arr, scale){ (p, v) => Rectangle(120, 80, v).fillDrawTextActive(p.colour, p, p.toString, 24, 2.0) }
  val tiles: GraphicElems = grid.mapPolygons(scale){(c: Cood, p: Polygon) => p.active(c) }
  def thisTop(): Unit = reTop(Refs(status))


  mainMouseUp =
    { case (LeftButton, cl, v) =>
      { selected = cl
        statusText = selected.headToStringElse("Nothing Selected")
        thisTop()
      }
      case _ => deb("Hi")
    }
  thisTop()
  mainRepaint(tiles ++ cenSideVertCoodText(grid, scale) ++ units)
}