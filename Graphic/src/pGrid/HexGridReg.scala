package ostrat
package pGrid
import geom._

/* A Regular hex grid where the rows have the same length, except div4rem2 rows may differ in length by 1 from div4rem0 rows. A div4rem2 row is
* where the y coordinate divided by 4 has a remainder of 2. */
case class HexGridReg(cTileMin: Int, cTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGrid with AltGridReg
{
  override def xRatio: Double = HexGrid.xRatio
  def cCen: Double = (cTileMin + cTileMax) / 2.0

  def coodCen = Vec2(cCen, yCen)


  def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
  //def coodToVec2Rel(cood: Cood): Vec2 = coodToVec2(cood) - cen

  /* Override methods */

  def sideLinesAllRel : Line2s = tilesAllFlatMap{cood =>
    val c1: Coods = sideCoodsOfTile(cood)
    val c2s: Line2s = c1.map(orig => HexGrid.sideCoodToLineRel(orig, cen))
    c2s
  }

  override def sideCoodsOfTile(tileCood: Cood): Coods = HexGrid.sideCoodsOfTile(tileCood)

  override def sideCoodToCoodLine(sideCood: ostrat.pGrid.Cood): ostrat.pGrid.CoodLine = HexGrid.sideCoodToCoodLine(sideCood)
}