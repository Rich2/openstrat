package ostrat
package pGrid
import geom._

/** A tile Grid where the x positions of the tiles lie midway between the x postions of the tiles on the rows above and below. */
case class RectAltGrid (cTileMin: Int, cTileMax: Int, yTileMin: Int, yTileMax: Int, xRatio: Double) extends AltGridReg
{ def yDivisor = (yTileMax - yTileMin) / 2.0 + 2
  def xDivisor = (cTileMax - cTileMin) / 2.0 + 2
  override def xCen: Double = (cTileMin + cTileMax) / 2.0 * xRatio / xDivisor
  override def yCen: Double = (yTileMin + yTileMax) / 2.0 / yDivisor
  override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)
  override def coodToVec2(cood: Cood): Vec2 = Vec2(cood.c * xRatio / xDivisor, cood.y / yDivisor)
  override def sideCoodToCoodLine(sideCood: ostrat.pGrid.Cood): CoodLine = SquareGrid.sideCoodToCoodLine(sideCood)
}
