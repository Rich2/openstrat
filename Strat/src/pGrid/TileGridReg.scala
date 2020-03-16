package ostrat
package pGrid
import geom._
trait TileGridReg extends TileGrid
{
  /** Returns an Array based collection of the grid side lines. */
  def sideLinesAll: Line2s = ???
  def sideCoodsAll: Coods = ???
  def sideCoodsOfTile(tileCood: Cood): Coods
  /** This gives the tile grid lines in a single colour and line width. */
  def sideLinesAllDraw(lineWidth: Double = 2.0, colour: Colour = Colour.Black): LinesDraw = LinesDraw(sideLinesAll, lineWidth, colour)
  /** The centre of the grid by the x coordinate. */
  def xCen: Double = (xTileMin + xTileMax) / 2.0
}
