package ostrat
import geom._, pGrid._

package object gOne
{
  def cenSideVertCoodText(grid: TileGrid, scale: Double): Refs[PaintElem] =
  { val cenTexts = grid.tilesCoodVecMap(scale){ (c, v) =>  TextGraphic(c.yxStr, 26, v) }
    val sls: LinesDraw = grid.sideLinesAll(scale).draw(2.0)
    val sideTexts = grid.sidesCoodVecMap(scale){ (c, v) =>  TextGraphic(c.yxStr, 22, v, Colour.Blue) }
    val vertTexts = grid.vertsCoodVecMap(scale){ (c, v) =>  TextGraphic(c.yxStr, 20, v, Colour.Red) }

    cenTexts +- sls ++ sideTexts ++ vertTexts
  }
}
