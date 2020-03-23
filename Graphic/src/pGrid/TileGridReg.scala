package ostrat
package pGrid
import geom._, Colour.Black

trait TileGridReg extends TileGrid
{
  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMin: Int

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMax: Int

  def sideCoodsOfTile(tileCood: Cood): Coods
  def sideCoodToCoodLine(sideCood: Cood): CoodLine

  final def sideLinesAll(scale: Double = 1.0, relPosn: Vec2 = Vec2Z) : Line2s = tilesAllFlatMap { cood =>
    val c1: Coods = sideCoodsOfTile(cood)
    val c2s: Line2s = c1.map(orig => sideCoodToLine(orig, scale, relPosn))
    c2s
  }

  /** This gives the tile grid lines in a single colour and line width. */
  def sideLinesAllDraw(scale: Double, lineWidth: Double = 2.0, colour: Colour = Colour.Black, relPosn: Vec2): LinesDraw =
    LinesDraw(sideLinesAll(scale, relPosn), lineWidth, colour)
  /** The centre of the grid by the x coordinate. */
  //def xCen: Double = (xTileMin + xTileMax) / 2.0

}
