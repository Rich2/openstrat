package ostrat
package pGrid

trait TileGridReg extends TileGrid
{
  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMin: Int

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio form column coordinate to x. */
  def cTileMax: Int
}
