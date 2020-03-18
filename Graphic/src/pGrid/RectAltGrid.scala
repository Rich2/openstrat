package ostrat
package pGrid
import geom._

/** A tile Grid where the x positions of the tiles lie midway between the x postions of the tiles on the rows above and below. */
case class RectAltGrid (cTileMin: Int, cTileMax: Int, yTileMin: Int, yTileMax: Int, xRatio: Double) extends AltGridReg
{
  override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)
  override def coodToVec2(cood: Cood): Vec2 = Vec2(cood.c * xRatio, cood.y)
  override def sideCoodToCoodLine(sideCood: ostrat.pGrid.Cood): CoodLine = SquareGrid.sideCoodToCoodLine(sideCood)
}
