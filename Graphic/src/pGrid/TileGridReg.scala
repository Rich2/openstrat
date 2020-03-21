package ostrat
package pGrid
import geom._

trait TileGridReg extends TileGrid
{
  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMin: Int

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMax: Int

  def sideCoodsAll: Coods = ???

  def sideCoodsOfTile(tileCood: Cood): Coods
  def sideCoodToCoodLine(sideCood: Cood): CoodLine





  final def sideCoodToLine(sideCood: Cood): Line2 = sideCoodToCoodLine(sideCood).toLine2(coodToVec2(_))

  final def sideLinesAll : Line2s = tilesAllFlatMap { cood =>
    val c1: Coods = sideCoodsOfTile(cood)
    val c2s: Line2s = c1.map(orig => sideCoodToLine(orig))
    c2s
  }

  /** This gives the tile grid lines in a single colour and line width. */
  def sideLinesAllDraw(lineWidth: Double = 2.0, colour: Colour = Colour.Black): LinesDraw = LinesDraw(sideLinesAll, lineWidth, colour)
  /** The centre of the grid by the x coordinate. */
  //def xCen: Double = (xTileMin + xTileMax) / 2.0

}
