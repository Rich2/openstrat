package ostrat
package pGrid
import geom._

/* A Regular hex grid where the rows have the same length, except div4rem2 rows may differ in length by 1 from div4rem0 rows. A div4rem2 row is
* where the y coordinate divided by 4 has a remainder of 2. */
case class HexGridReg(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGrid with AltGridReg
{
  def yCenNoAdj: Double = (yTileMin + yTileMax) / 2.0
  def yCenAdj: Double = yCenNoAdj * HexGrid.yRatio
  def cen = Vec2(xCen, yCenNoAdj)
  def cenAdj = Vec2(xCen, yCenAdj)

  def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
  def coodToVec2Rel(cood: Cood): Vec2 = coodToVec2(cood) - cenAdj

  /* Override methods */

  override def sideLinesAll : Line2s = tilesAllFlatMap{cood =>
    val c1: Coods = HexGrid.sideCoodsOfTile(cood)
    val c2s: Line2s = c1.map(orig => HexGrid.sideCoodToLine(orig))
    c2s
  }

  override def sideCoodsOfTile(tileCood: Cood): Coods = HexGrid.adjTileCoodsOfTile(tileCood: Cood)
}