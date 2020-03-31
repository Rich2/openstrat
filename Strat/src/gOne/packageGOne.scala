package ostrat
import geom._, pGrid._

package object gOne
{
  def cenSideVertCoodText(grid: TileGrid, scale: Double): Refs[PaintElem] =
  {
    val cenTexts = grid.mapVecs(scale)((r, v) => TextGraphic(r.ycStr, 26, v))
    val sls: LinesDraw = grid.sideLinesAll(scale).draw(2.0)
    val sideTexts = grid.sidesMapRoordVec(scale){ (r, v) =>  TextGraphic(r.ycStr, 22, v, Colour.Blue) }
    val vertTexts = grid.vertsMapRoordVec(scale){ (r, v) =>  TextGraphic(r.ycStr, 20, v, Colour.Red) }

    cenTexts +- sls ++ sideTexts ++ vertTexts
  }
}
