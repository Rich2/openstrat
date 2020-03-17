package ostrat
package pGrid

/** A tile Grid where the x positions of the tiles lie midway between the x postions of the tiles on the rows above and below. */
case class RectAltGrid (xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends AltGridReg
{
  override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)
}
